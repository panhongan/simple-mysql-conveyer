package com.github.panhongan.bean2sql.condition;

import lombok.Getter;

/**
 * 条件操作符定义.
 *
 * @author panhongan
 * @since 2019.7.8
 * @version 1.0
 */

@Getter
public enum SqlConditionOperator {
    EQUAL("01", "=", "等于"),
    NOT_EQUAL("02", "!=", "不等于"),
    LESS("03", "<", "小于"),
    LESS_OR_EQUAL("04", "<=", "小于或等于"),
    GREATER("05", ">", "大于"),
    GREATER_OR_EQUAL("06", ">=", "大于或等于"),
    BETWEEN("07", " between and ", "BETWEEN"),
    LIKE("08", " like ", "LIKE"),
    OR("09", " or ", "OR"),
    AND("10", " and ", "AND"),
    JSON_EQUAL("11", "json_contains", "json_contains"),
    JSON_LIKE("12", "json_search", "json_search"),
    ;

    private final String code;
    private final String operator;
    private final String desc;

    SqlConditionOperator(String code, String operator, String desc) {
        this.code = code;
        this.operator = operator;
        this.desc = desc;
    }

    public static boolean isComparableType(SqlConditionOperator sqlConditionOperator) {
        return (SqlConditionOperator.LESS == sqlConditionOperator
                || SqlConditionOperator.LESS_OR_EQUAL == sqlConditionOperator
                || SqlConditionOperator.GREATER == sqlConditionOperator
                || SqlConditionOperator.GREATER_OR_EQUAL == sqlConditionOperator);
    }

    public static boolean isNotComparableType(SqlConditionOperator sqlConditionOperator) {
        return !isComparableType(sqlConditionOperator);
    }

    public static boolean isEqualType(SqlConditionOperator sqlConditionOperator) {
        return (SqlConditionOperator.EQUAL == sqlConditionOperator
                || SqlConditionOperator.NOT_EQUAL == sqlConditionOperator);
    }

    public static boolean isNotEqualType(SqlConditionOperator sqlConditionOperator) {
        return !isEqualType(sqlConditionOperator);
    }
}
