package com.github.panhongan.bean2sql;

import com.github.panhongan.bean2sql.condition.SqlConditionOperator;
import com.github.panhongan.commons.MysqlConveyerException;
import com.github.panhongan.utils.datetime.DateUtils;
import com.github.panhongan.utils.naming.NamingUtils;
import com.github.panhongan.utils.reflect.ReflectUtils;
import com.github.panhongan.bean2sql.condition.SqlCondition;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author panhongan
 * @since 2019.7.8
 * @version 1.0
 */

public class Bean2SqlUtils {

    public static final String AND_STR = " and ";

    public static final String COMMA_STR = ",";

    public static final String PLACE_HOLDER = "?";

    public static final String LEFT_BRACKET = "(";

    public static final String RIGHT_BRACKET = ")";

    private static Map<Class, String> selectFieldsStringMap = new ConcurrentHashMap<>();

    public static <T> Pair<String, Map<Integer, String>> getEqualTypeConditionSql(T conditionObj, SqlConditionOperator sqlConditionOperator) {
        if (conditionObj == null || SqlConditionOperator.isNotEqualType(sqlConditionOperator)) {
            return SqlCondition.EMPTY_CONDITION_SQL;
        }

        StringBuilder conditionSql = new StringBuilder();
        Map<Integer, String> values = new HashMap<>();
        int index = 1;

        try {
            Collection<Field> fields = ReflectUtils.getClassBeanFieldFast(conditionObj.getClass());
            for (Field field : fields) {
                Object value = field.get(conditionObj);
                if (Objects.isNull(value)) {
                    continue;
                }

                String type = field.getType().getSimpleName();
                if (!checkEqualFieldType(type)) {
                    continue;
                }

                conditionSql.append(NamingUtils.camel2Hung(field.getName()));
                conditionSql.append(sqlConditionOperator.getOperator());
                conditionSql.append(PLACE_HOLDER);
                conditionSql.append(AND_STR);

                // 日期格式化
                if (type.equals("Date")) {
                    values.put(index++, DateUtils.date2Str((Date) value, DateUtils.SETTLE_PATTERN));
                } else {
                    values.put(index++, value.toString());
                }
            }
        } catch (Exception e) {
            throw new MysqlConveyerException(e);
        }

        if (conditionSql.length() > 0) {
            int pos = conditionSql.lastIndexOf(AND_STR);
            conditionSql.delete(pos, pos + AND_STR.length());

            // 首尾增加()
            conditionSql.insert(0, LEFT_BRACKET);
            conditionSql.append(RIGHT_BRACKET);
        }

        return Pair.of(conditionSql.toString(), values);
    }

    public static <T> Pair<String, Map<Integer, String>> getComparableConditionSql(T conditionObj, SqlConditionOperator sqlConditionOperator) {
        if (conditionObj == null || SqlConditionOperator.isNotComparableType(sqlConditionOperator)) {
            return SqlCondition.EMPTY_CONDITION_SQL;
        }

        StringBuilder conditionSql = new StringBuilder();
        Map<Integer, String> values = new HashMap<>();
        int index = 1;

        try {
            Collection<Field> fields = ReflectUtils.getClassBeanFieldFast(conditionObj.getClass());
            for (Field field : fields) {
                Object value = field.get(conditionObj);
                if (Objects.isNull(value)) {
                    continue;
                }

                String type = field.getType().getSimpleName();
                if (!checkComparableFieldType(type)) {
                    continue;
                }

                conditionSql.append(NamingUtils.camel2Hung(field.getName()));
                conditionSql.append(sqlConditionOperator.getOperator());
                conditionSql.append(PLACE_HOLDER);
                conditionSql.append(AND_STR);

                if (type.equals("Date")) {
                    values.put(index++, DateUtils.date2Str((Date) value, DateUtils.SETTLE_PATTERN));
                } else {
                    values.put(index++, value.toString());
                }
            }
        } catch (Exception e) {
            throw new MysqlConveyerException(e);
        }

        if (conditionSql.length() > 0) {
            int pos = conditionSql.lastIndexOf(AND_STR);
            conditionSql.delete(pos, pos + AND_STR.length());

            // 首尾增加()
            conditionSql.insert(0, LEFT_BRACKET);
            conditionSql.append(RIGHT_BRACKET);
        }

        return Pair.of(conditionSql.toString(), values);
    }

    public static <T extends  Comparable<T>> Pair<String, Map<Integer, String>> getBetweenAndConditionSql(String fieldName, T begin, T end) {
        if (StringUtils.isEmpty(fieldName) || Objects.isNull(begin) || Objects.isNull(end)) {
            return SqlCondition.EMPTY_CONDITION_SQL;
        }

        StringBuilder sql = new StringBuilder();
        sql.append(fieldName);
        sql.append(" between ? and ?");

        // 首尾增加()
        sql.insert(0, LEFT_BRACKET);
        sql.append(RIGHT_BRACKET);

        Map<Integer, String> values = new HashMap<>();
        values.put(1, begin.toString());
        values.put(2, end.toString());

        return Pair.of(sql.toString(), values);
    }

    public static <T> Pair<String, Map<Integer, String>> getLikeConditionSql(T conditionObj) {
        if (Objects.isNull(conditionObj)) {
            return SqlCondition.EMPTY_CONDITION_SQL;
        }

        StringBuilder sql = new StringBuilder();
        Map<Integer, String> values = new HashMap<>();
        int index = 1;

        try {
            Collection<Field> fields = ReflectUtils.getClassBeanFieldFast(conditionObj.getClass());
            for (Field field : fields) {
                Object value = field.get(conditionObj);
                if (Objects.isNull(value)) {
                    continue;
                }

                // 必须是String类型字段或Date
                String type = field.getType().getSimpleName();
                if (!checkLikeFieldType(type)) {
                    continue;
                }

                // 不能为null且不能是空字符串
                String strVal = value.toString();
                if (StringUtils.isEmpty(strVal)) {
                    continue;
                }

                sql.append(NamingUtils.camel2Hung(field.getName()));
                sql.append(" like ");
                sql.append(PLACE_HOLDER);
                sql.append(AND_STR);

                if (type.equals("String")) {
                    values.put(index++, "%" + strVal + "%");
                } else {
                    values.put(index++, "%" + DateUtils.date2Str((Date) value, DateUtils.SETTLE_PATTERN).substring(0, 10) + "%");
                }
            }
        } catch (Exception e) {
            throw new MysqlConveyerException(e);
        }

        if (sql.length() > 0) {
            int pos = sql.lastIndexOf(AND_STR);
            sql.delete(pos, pos + AND_STR.length());

            // 首尾增加()
            sql.insert(0, LEFT_BRACKET);
            sql.append(RIGHT_BRACKET);
        }

        return Pair.of(sql.toString(), values);
    }

    public static <T> Pair<String, Map<Integer, String>> getInsertSqlByObj(T obj) {
        if (Objects.isNull(obj)) {
            return SqlCondition.EMPTY_CONDITION_SQL;
        }

        StringBuilder fieldNames = new StringBuilder();
        StringBuilder placeHolders = new StringBuilder();
        Map<Integer, String> values = new HashMap<>();
        int index = 1;

        try {
            Collection<Field> fields = ReflectUtils.getClassBeanFieldFast(obj.getClass());
            for (Field field : fields) {
                Object value = field.get(obj);
                if (Objects.isNull(value)) {
                    continue;
                }

                String type = field.getType().getSimpleName();
                if (!checkEqualFieldType(type)) {
                    continue;
                }

                fieldNames.append(NamingUtils.camel2Hung(field.getName()));
                fieldNames.append(COMMA_STR);

                placeHolders.append(PLACE_HOLDER);
                placeHolders.append(COMMA_STR);

                if (type.equals("Date")) {
                    values.put(index++, DateUtils.date2Str((Date) value, DateUtils.SETTLE_PATTERN));
                } else {
                    values.put(index++, value.toString());
                }
            }
        } catch (Exception e) {
            throw new MysqlConveyerException(e);
        }

        if (fieldNames.length() == 0) {
            throw new MysqlConveyerException("no value for object : " + obj);
        }

        if (fieldNames.length() > 0) {
            fieldNames.delete(fieldNames.length() - 1, fieldNames.length());
            placeHolders.delete(placeHolders.length() - 1, placeHolders.length());
        }

        StringBuilder sql = new StringBuilder();
        sql.append(LEFT_BRACKET);
        sql.append(fieldNames);
        sql.append(RIGHT_BRACKET);
        sql.append(" values");
        sql.append(LEFT_BRACKET);
        sql.append(placeHolders);
        sql.append(RIGHT_BRACKET);

        return Pair.of(sql.toString(), values);
    }

    public static <T> Pair<String, Map<Integer, String>> getUpdateSqlByObj(long id, T obj) {
        if (obj == null) {
            return SqlCondition.EMPTY_CONDITION_SQL;
        }

        StringBuilder sql = new StringBuilder();
        Map<Integer, String> values = new HashMap<>();
        int index = 1;

        try {
            Collection<Field> fields = ReflectUtils.getClassBeanFieldFast(obj.getClass());
            for (Field field : fields) {
                Object value = field.get(obj);
                if (Objects.isNull(value)) {
                    continue;
                }

                sql.append(NamingUtils.camel2Hung(field.getName()));
                sql.append("=");
                sql.append(PLACE_HOLDER);
                sql.append(COMMA_STR);

                String type = field.getType().getSimpleName();
                if (type.equals("Date")) {
                    values.put(index++, DateUtils.date2Str((Date) value, DateUtils.SETTLE_PATTERN));
                } else {
                    values.put(index++, value.toString());
                }
            }
        } catch (Exception e) {
            throw new MysqlConveyerException(e);
        }

        if (sql.length() == 0) {
            throw new MysqlConveyerException("no value for object : " + obj);
        }

        // delete ','
        sql.delete(sql.length() - 1, sql.length());

        // where id=?
        sql.append(" where id=");
        sql.append(PLACE_HOLDER);
        values.put(index, String.valueOf(id));

        return Pair.of(sql.toString(), values);
    }

    public static String getSelectFieldsStringFast(Class c) {
        String selectFieldsStr = selectFieldsStringMap.get(c);
        if (StringUtils.isNotEmpty(selectFieldsStr)) {
            return selectFieldsStr;
        }

        return selectFieldsStringMap.computeIfAbsent(c, k -> getSelectFieldsString(k));
    }

    static String getSelectFieldsString(Class c) {
        if (Objects.isNull(c)) {
            return null;
        }

        StringBuilder builder = new StringBuilder();

        try {
            Collection<Field> fields = ReflectUtils.getClassBeanFieldFast(c);
            for (Field field : fields) {
                builder.append(NamingUtils.camel2Hung(field.getName()));
                builder.append(COMMA_STR);
            }
        } catch (Exception e) {
            throw new MysqlConveyerException(e);
        }

        if (builder.length() > 0) {
            builder.delete(builder.length() - 1, builder.length());
        } else {
            throw new MysqlConveyerException("no fields for object : " + c.getName());
        }

        return builder.toString();
    }

    public static boolean checkEqualFieldType(String type) {
        return ("Long".equals(type) || "Integer".equals(type) || "Short".equals(type) ||
                "Float".equals(type) || "Double".equals(type) ||
                "Date".equals(type) || "String".equals(type));
    }

    public static boolean checkComparableFieldType(String type) {
        return ("Long".equals(type) || "Integer".equals(type) || "Short".equals(type) ||
                "Float".equals(type) || "Double".equals(type) || "Date".equals(type));
    }

    public static boolean checkLikeFieldType(String type) {
        return ("String".equals(type) || "Date".equals(type));
    }
}
