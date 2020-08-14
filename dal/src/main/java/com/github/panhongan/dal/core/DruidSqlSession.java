package com.github.panhongan.dal.core;

import com.github.panhongan.common.Bean2SqlException;
import com.github.panhongan.common.utils.MysqlUtils;
import com.github.panhongan.common.utils.NamingUtils;
import com.github.panhongan.common.utils.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author panhongan
 * @since 2019.7.14
 * @version 1.0
 */

@Service
@Slf4j
public class DruidSqlSession {

    @Autowired
    private TransactionManagerEx transactionManagerEx;

    public long getMaxRowId(String sql, Map<Integer, String> values) {
        return 1L;
    }

    public List<Long> insert(String sql, Map<Integer, String> values) {
        return Arrays.asList(1L);
    }

    public int update(String sql, Map<Integer, String> values) {
        return 1;
    }

    public <D> List<D> select(String sql, Map<Integer, String> values, Class<D> c) throws Bean2SqlException {
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
                }
            }


            return results;
        } catch (Throwable t) {
            log.error("sql = {}, values = {},class = {}, err = {}", sql, values, c.getName(), t);
            throw new RuntimeException(t);
        } finally {
            MysqlUtils.closeResultSet(rs);
            MysqlUtils.closeStatement(ps);
            MysqlUtils.closeConnection(conn);
        }
    }

    public int getCount(String countSql, Map<Integer, String> values) throws Bean2SqlException {
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
        } catch (Exception e) {
            throw new Bean2SqlException(e);
        } finally {
            MysqlUtils.closeResultSet(rs);
            MysqlUtils.closeStatement(ps);
            MysqlUtils.closeConnection(conn);
        }
    }
}
