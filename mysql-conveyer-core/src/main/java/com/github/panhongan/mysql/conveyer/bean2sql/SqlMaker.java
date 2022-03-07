package com.github.panhongan.mysql.conveyer.bean2sql;

import com.github.panhongan.mysql.conveyer.bean2sql.condition.SqlCondition;
import com.github.panhongan.mysql.conveyer.bean2sql.orderby.OrderBy;
import com.github.panhongan.mysql.conveyer.bean2sql.table.PageContext;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author panhongan
 * @since 2020.9.18
 * @version 1.0
 */

public class SqlMaker {

    public static <D> Pair<String, Map<Integer, String>> makeQueryByIdSql(String tableName, long id, Class<D> c) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(tableName), "tableName is empty");

        StringBuilder builder = new StringBuilder();
        builder.append("select ");
        builder.append(Bean2SqlUtils.getSelectFieldsStringFast(c));
        builder.append(" from ");
        builder.append(tableName);
        builder.append(" where id=?");

        Map<Integer, String> values = new HashMap<>();
        values.put(1, String.valueOf(id));

        return Pair.of(builder.toString(), values);
    }

    public static Pair<String, Map<Integer, String>> makeMaxRowIdSql(String tableName, SqlCondition sqlCondition) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(tableName), "tableName is empty");

        StringBuilder sql = new StringBuilder();
        sql.append("select max(id) from ");
        sql.append(tableName);

        Pair<String, Map<Integer, String>> pair = SqlCondition.EMPTY_CONDITION_SQL;
        if (sqlCondition != null) {
            pair = sqlCondition.conditionSql();
        }

        if (StringUtils.isNotEmpty(pair.getLeft())) {
            sql.append(" where ");
            sql.append(pair.getLeft());
        }

        return Pair.of(sql.toString(), pair.getRight());
    }

    public static <D> Pair<String, Map<Integer, String>> makeInsertSql(String tableName, D obj) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(tableName), "tableName is empty");
        Preconditions.checkNotNull(obj, "obj is null");

        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getInsertSqlByObj(obj);
        String sql = "insert into " + tableName + pair.getLeft();
        return Pair.of(sql, pair.getRight());
    }

    public static Pair<String, Map<Integer, String>> makeDeleteByIdSql(String tableName, long id) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(tableName), "tableName is empty");
        Preconditions.checkArgument(id > 0, "id should be : >0");

        String sql = "delete from " + tableName + " where id=?";
        Map<Integer, String> values = new HashMap<>();
        values.put(1, String.valueOf(id));

        return Pair.of(sql, values);
    }

    public static <D> Pair<String, Map<Integer, String>> makeUpdateSql(String tableName, long id, D newObj) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(tableName), "tableName is empty");
        Preconditions.checkArgument(id > 0, "id should be : >0");
        Preconditions.checkNotNull(newObj, "obj is null");

        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getUpdateSqlByObj(id, newObj);
        String sql = "update " + tableName + " set " + pair.getLeft();
        return Pair.of(sql, pair.getRight());
    }

    public static <D> Pair<String, Map<Integer, String>> makeQueryByConditionSql(String tableName,
                                                                                 SqlCondition sqlCondition,
                                                                                 OrderBy orderBy,
                                                                                 Class<D> c) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(tableName), "tableName is empty");
        Preconditions.checkNotNull(sqlCondition, "sqlCondition is null");

        Pair<String, Map<Integer, String>> pair = sqlCondition.conditionSql();
        StringBuilder builder = new StringBuilder();
        builder.append("select ");
        builder.append(Bean2SqlUtils.getSelectFieldsStringFast(c));
        builder.append(" from ");
        builder.append(tableName);
        if (StringUtils.isNotEmpty(pair.getLeft())) {
            builder.append(" where ");
            builder.append(pair.getLeft());
        }

        if (Objects.nonNull(orderBy)) {
            builder.append(" ");
            builder.append(orderBy.toOrderBy());
        }

        return Pair.of(builder.toString(), pair.getRight());
    }

    public static <D> Pair<Pair<String, String>, Map<Integer, String>> makeQueryByPageSql(String tableName,
                                                                                          SqlCondition sqlCondition,
                                                                                          PageContext pageContext,
                                                                                          OrderBy orderBy,
                                                                                          Class<D> c) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(tableName), "tableName is empty");
        Preconditions.checkNotNull(sqlCondition, "sqlCondition is null");
        Preconditions.checkNotNull(pageContext, "pageContext is null");
        Preconditions.checkNotNull(c, "c is null");

        Pair<String, Map<Integer, String>> pair = sqlCondition.conditionSql();

        int beginOffset = (pageContext.getCurrPage() - 1) * pageContext.getPageSize();

        StringBuilder baseSqlBuilder = new StringBuilder();
        baseSqlBuilder.append("from ");
        baseSqlBuilder.append(tableName);
        if (StringUtils.isNotEmpty(pair.getLeft())) {
            baseSqlBuilder.append(" where ");
            baseSqlBuilder.append(pair.getLeft());
        }

        if (Objects.nonNull(orderBy)) {
            baseSqlBuilder.append(" ");
            baseSqlBuilder.append(orderBy.toOrderBy());
        }

        String baseSql = baseSqlBuilder.toString();
        String countSql = "select count(id) " + baseSql;
        String pageSql = "select " + Bean2SqlUtils.getSelectFieldsStringFast(c) + " " + baseSql + " limit " + beginOffset + "," + pageContext.getPageSize();
        return Pair.of(Pair.of(countSql, pageSql), pair.getRight());
    }
}
