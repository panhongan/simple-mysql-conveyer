package com.github.panhongan.condition;


import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum ConditionOperator {
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

    ConditionOperator(String code, String operator, String desc) {
        this.code = code;
        this.operator = operator;
        this.desc = desc;
    }

    public static ConditionOperator of(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }

        for (ConditionOperator value : ConditionOperator.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }

        return null;
    }

    public static boolean isNumberType(ConditionOperator conditionOperator) {
        return (ConditionOperator.LESS == conditionOperator ||
            ConditionOperator.LESS_OR_EQUAL == conditionOperator ||
            ConditionOperator.GREATER == conditionOperator||
            ConditionOperator.GREATER_OR_EQUAL == conditionOperator);
    }

    public static boolean isNotNumberType(ConditionOperator conditionOperator) {
        return !isNumberType(conditionOperator);
    }

    public static boolean isEqualType(ConditionOperator conditionOperator) {
        return (ConditionOperator.EQUAL == conditionOperator ||
            ConditionOperator.NOT_EQUAL == conditionOperator);
    }

    public static boolean isNotEqualType(ConditionOperator conditionOperator) {
        return !isEqualType(conditionOperator);
    }

}
