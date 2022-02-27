package com.github.panhongan.bean2sql.table;

import com.github.panhongan.bean2sql.transaction.TransactionManagerEx;
import com.github.panhongan.commons.MysqlConveyerException;
import com.github.panhongan.utils.function.ThrowableFunction;
import com.github.panhongan.utils.sql.SqlUtils;
import com.github.panhongan.utils.naming.NamingUtils;
import com.github.panhongan.utils.reflect.ReflectUtils;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.JdbcTransactionObjectSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionStatus;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author panhongan
 * @since 2019.7.14
 * @version 1.0
 */

@Service
@Slf4j
public class SqlExecutor {

    private static Map<String, ThrowableFunction<Pair<ResultSet, String>, Object, Throwable>> mapFunc = new HashMap<>();

    static {
        mapFunc.put("Boolean", p -> p.getLeft().getBoolean(p.getRight()));
        mapFunc.put("String", p -> p.getLeft().getString(p.getRight()));
        mapFunc.put("Short", p -> p.getLeft().getShort(p.getRight()));
        mapFunc.put("Integer", p -> p.getLeft().getInt(p.getRight()));
        mapFunc.put("Long", p -> p.getLeft().getLong(p.getRight()));
        mapFunc.put("Float", p -> p.getLeft().getFloat(p.getRight()));
        mapFunc.put("Double", p -> p.getLeft().getDouble(p.getRight()));
        mapFunc.put("Date", p -> p.getLeft().getTimestamp(p.getRight()));
    }

    @Autowired
    private TransactionManagerEx transactionManagerEx;

    public long getMaxRowId(String sql, Map<Integer, String> values) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            long maxId = 0L;

            DataSource dataSource = ((DataSourceTransactionManager) transactionManagerEx.getTransactionTemplate().getTransactionManager()).getDataSource();
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            for (Map.Entry<Integer, String> entry : values.entrySet()) {
                ps.setString(entry.getKey(), entry.getValue());
            }

            rs = ps.executeQuery();
            if (rs.next()) {
                maxId = rs.getLong(1);
            }

            return maxId;
        } catch (Throwable t) {
            log.error("sql = {}, values = {}", sql, values, t);
            throw new MysqlConveyerException(t);
        } finally {
            SqlUtils.closeResultSet(rs);
            SqlUtils.closeStatement(ps);
            SqlUtils.closeConnection(conn);
        }
    }

    public List<Long> insert(String sql, Map<Integer, String> values) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            TransactionStatus transactionStatus = transactionManagerEx.getCurrentTransactionStatus();
            Preconditions.checkNotNull(transactionStatus, "insert() operation not wrapped by transaction");

            // connection必须是来自事务
            JdbcTransactionObjectSupport txobj = (JdbcTransactionObjectSupport) ((DefaultTransactionStatus) transactionStatus).getTransaction();
            Connection conn = txobj.getConnectionHolder().getConnection();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (Map.Entry<Integer, String> entry : values.entrySet()) {
                ps.setString(entry.getKey(), entry.getValue());
            }

            ps.executeUpdate();

            List<Long> ids = new ArrayList<>();
            rs = ps.getGeneratedKeys();
            while (rs.next()) {
                ids.add(rs.getLong(1));
            }

            return ids;
        } catch (Throwable t) {
            log.error("sql = {}, values = {}", sql, values, t);
            throw new MysqlConveyerException(t);
        } finally {
            SqlUtils.closeResultSet(rs);
            SqlUtils.closeStatement(ps);
        }
    }

    public int update(String sql, Map<Integer, String> values) {
        PreparedStatement ps = null;

        try {
            TransactionStatus transactionStatus = transactionManagerEx.getCurrentTransactionStatus();
            Preconditions.checkNotNull(transactionStatus, "update() operation not triggered by transaction");

            // connection必须是来自事务
            JdbcTransactionObjectSupport txobj = (JdbcTransactionObjectSupport) ((DefaultTransactionStatus) transactionStatus).getTransaction();
            Connection conn = txobj.getConnectionHolder().getConnection();
            ps = conn.prepareStatement(sql);
            for (Map.Entry<Integer, String> entry : values.entrySet()) {
                ps.setString(entry.getKey(), entry.getValue());
            }

            int affectedRows = ps.executeUpdate();  // 生效的行数
            return affectedRows;
        } catch (Throwable t) {
            log.error("sql = {}, values = {}", sql, values, t);
            throw new MysqlConveyerException(t);
        } finally {
            SqlUtils.closeStatement(ps);
        }
    }

    public <D> List<D> select(String sql, Map<Integer, String> values, Class<D> c) throws MysqlConveyerException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            List<D> results = new ArrayList<>();
            Collection<Field> fields = ReflectUtils.getClassBeanFieldFast(c);

            DataSource dataSource = ((DataSourceTransactionManager) transactionManagerEx.getTransactionTemplate().getTransactionManager()).getDataSource();
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            for (Map.Entry<Integer, String> entry : values.entrySet()) {
                ps.setString(entry.getKey(), entry.getValue());
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                D doObj = ReflectUtils.newInstance(c);

                for (Field field : fields) {
                    String colName = NamingUtils.camel2Hung(field.getName());
                    String type = field.getType().getSimpleName();

                    ThrowableFunction func = mapFunc.get(type);
                    Preconditions.checkNotNull(func, "func is null, type = " + type);
                    Object value = func.apply(Pair.of(rs, colName));

                    field.set(doObj, value);
                }

                results.add(doObj);
            }

            return results;
        } catch (Throwable t) {
            log.error("sql = {}, values = {}, class = {}", sql, values, c.getName(), t);
            throw new MysqlConveyerException(t);
        } finally {
            SqlUtils.closeResultSet(rs);
            SqlUtils.closeStatement(ps);
            SqlUtils.closeConnection(conn);
        }
    }

    public int getCount(String countSql, Map<Integer, String> values) throws MysqlConveyerException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            int count = 0;

            DataSource dataSource = ((DataSourceTransactionManager) transactionManagerEx.getTransactionTemplate().getTransactionManager()).getDataSource();
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(countSql);
            for (Map.Entry<Integer, String> entry : values.entrySet()) {
                ps.setString(entry.getKey(), entry.getValue());
            }

            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

            return count;
        } catch (Throwable t) {
            log.error("sql = {}, values = {}", countSql, values, t);
            throw new MysqlConveyerException(t);
        } finally {
            SqlUtils.closeResultSet(rs);
            SqlUtils.closeStatement(ps);
            SqlUtils.closeConnection(conn);
        }
    }
}
