package com.github.panhongan.dal;

import com.github.panhongan.common.Bean2SqlException;
import com.github.panhongan.common.page.PageContext;
import com.github.panhongan.common.page.PageResult;
import com.github.panhongan.common.utils.ObjectUtils;
import com.github.panhongan.condition.Bean2SqlUtils;
import com.github.panhongan.condition.sql.AndCondition;
import com.github.panhongan.condition.sql.EqualCondition;
import com.github.panhongan.condition.sql.SqlCondition;
import com.github.panhongan.dal.core.DruidSqlSession;
import lombok.extern.slf4j.Slf4j;
import net.sf.oval.constraint.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author panhongan
 * @since 2019.7.14
 * @version 1.0
 */

@Slf4j
public abstract class AbstractTableAccess<D> implements TableAccess<D> {

    @Autowired
    private DruidSqlSession druidSqlSession;

    /**
     * 数据库表
     *
     * @return
     */
    public abstract String getTable();

    @Override
    public long getMaxRowId(@NotNull SqlCondition sqlCondition) throws Bean2SqlException {
        ObjectUtils.validateObject(sqlCondition);

        Pair<String, Map<Integer, String>> pair = sqlCondition.conditionSql();
        StringBuilder sql = new StringBuilder();
        sql.append("select max(id) from ");
        sql.append(this.getTable());
        if (StringUtils.isNotEmpty(pair.getLeft())) {
            sql.append(" where ");
            sql.append(pair.getLeft());
        }

        log.info("getMaxRowId, sql = {}, values = {}", sql, pair.getRight());
        return druidSqlSession.getMaxRowId(sql.toString(), pair.getRight());
    }

    @Override
    public long insert(@NotNull D record) throws Bean2SqlException {
        ObjectUtils.validateObject(record);

        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getInsertSqlByObj(record);
        String sql = "insert into " + this.getTable() + pair.getLeft();
        log.info("insert, sql = {}, values = {}", sql, pair.getRight());
        return druidSqlSession.insert(sql, pair.getRight()).get(0);
    }

    @Override
    public int deleteById(long id) throws Bean2SqlException {
        String sql = "delete from " + this.getTable() + " where id=?";
        Map<Integer, String> values = new HashMap<>();
        values.put(1, String.valueOf(id));
        log.info("deleteById, sql = {}, values = {}", sql, values);
        return druidSqlSession.update(sql, values);
    }

    @Override
    public int update(long id, @NotNull D newRecord) throws Bean2SqlException {
        ObjectUtils.validateObject(newRecord);

        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getUpdateSqlByObj(id, newRecord);
        String sql = "update " + this.getTable() + " set " + pair.getLeft();
        log.info("update, sql = {}, values = {}", sql, pair.getRight());
        return druidSqlSession.update(sql, pair.getRight());
    }

    @Override
    public List<D> queryByCondition(@NotNull D condition) throws Bean2SqlException {
        ObjectUtils.validateObject(condition);
        return this.queryByCondition(condition, null);
    }

    @Override
    public List<D> queryByCondition(@NotNull D condition, SqlCondition sqlCondition) throws Bean2SqlException {
        ObjectUtils.validateObject(condition);

        SqlCondition andCondition = this.makeAndCondition(condition, sqlCondition);
        return this.queryByCondition(andCondition, (Class<D>) condition.getClass());
    }

    @Override
    public List<D> queryByCondition(@NotNull SqlCondition sqlCondition, Class<D> c) throws Bean2SqlException {
        ObjectUtils.validateObject(sqlCondition);

        Pair<String, Map<Integer, String>> pair = sqlCondition.conditionSql();
        StringBuilder builder = new StringBuilder();
        builder.append("select ");
        builder.append(Bean2SqlUtils.getSelectFieldsStringFast(c));
        builder.append(" from ");
        builder.append(this.getTable());
        if (StringUtils.isNotEmpty(pair.getLeft())) {
            builder.append(" where ");
            builder.append(pair.getLeft());
        }

        String sql = builder.toString();
        log.info("queryByCondition, sql = {}, values = {}", sql, pair.getRight());
        return druidSqlSession.select(sql, pair.getRight(), c);
    }

    @Override
    public PageResult<D> queryByPage(@NotNull D condition, @NotNull PageContext pageContext) throws Bean2SqlException {
        ObjectUtils.validateObject(condition);
        ObjectUtils.validateObject(pageContext);
        return this.queryByPage(condition, null, pageContext);
    }

    @Override
    public PageResult<D> queryByPage(@NotNull D condition, SqlCondition sqlCondition, @NotNull PageContext pageContext) throws Bean2SqlException {
        ObjectUtils.validateObject(condition);
        ObjectUtils.validateObject(pageContext);
        SqlCondition andCondition = this.makeAndCondition(condition, sqlCondition);
        return this.queryByPage(andCondition, pageContext, (Class<D>) condition.getClass());
    }

    @Override
    public PageResult<D> queryByPage(@NotNull SqlCondition sqlCondition, @NotNull PageContext pageContext, Class<D> c) throws Bean2SqlException {
        ObjectUtils.validateObject(sqlCondition);
        ObjectUtils.validateObject(pageContext);

        Pair<Pair<String, String>, Map<Integer, String>> pair = this.makeQueryByPageSql(sqlCondition, pageContext);

        // total count
        String countSql = "select count(id) " + pair.getLeft().getLeft();
        log.info("queryByPage, count sql = {}, values = {}", countSql, pair.getRight());
        int totalCount = druidSqlSession.getCount(countSql, pair.getRight());
        int totalPage = totalCount / pageContext.getPageSize() + (totalCount % pageContext.getPageSize() > 0 ? 1 : 0);

        // page data
        String pageSql = "select " + Bean2SqlUtils.getSelectFieldsStringFast(c) + " " + pair.getLeft().getRight();
        log.info("queryByPage, page sql = {}, values = {}", pageSql, pair.getRight());
        Collection<D> rows = druidSqlSession.select(pageSql, pair.getRight(), c);

        // page result
        PageResult<D> pageResult = new PageResult<>();
        pageResult.setCurrPage(pageContext.getCurrPage());
        pageResult.setPageSize(pageContext.getPageSize());
        pageResult.setTotalPage(totalPage);
        pageResult.setTotalCount(totalCount);
        pageResult.addAll(rows);
        return pageResult;
    }

    protected SqlCondition makeAndCondition(D condition, SqlCondition sqlCondition) {
        EqualCondition equalCondition = EqualCondition.builder().obj(condition).build();
        AndCondition andCondition = new AndCondition();
        andCondition.add(equalCondition).add(sqlCondition);
        return andCondition;
    }

    protected Pair<Pair<String, String>, Map<Integer, String>> makeQueryByPageSql(SqlCondition sqlCondition, PageContext pageContext) {
        Pair<String, Map<Integer, String>> pair = sqlCondition.conditionSql();
        int beginOffset = (pageContext.getCurrPage() - 1) * pageContext.getPageSize();
        String countSql = "from " + this.getTable() + (StringUtils.isNotEmpty(pair.getLeft()) ? " where " : "") + pair.getLeft();
        String pageSql = countSql + " limit " + beginOffset + "," + pageContext.getPageSize();
        return Pair.of(Pair.of(countSql, pageSql), pair.getRight());
    }
}
