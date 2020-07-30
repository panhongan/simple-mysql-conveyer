package com.github.panhongan.condition.common;

import com.github.panhongan.condition.ConditionOperator;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Bean2SqlUtils {

    public static final String AND_STR = " and ";

    public static final String COMMA_STR = ",";

    public static final String PLACE_HOLDER = "?";

    public static final String LEFT_BRACKET = "(";

    public static final String RIGHT_BRACKET = ")";

    public static <T> Pair<String, Map<Integer, String>> getEqualTypeConditionSql(T conditionObj, ConditionOperator conditionOperator) {
        StringBuilder conditionSql = new StringBuilder();
        Map<Integer, String> values = new HashMap<>();
        int index = 1;

        if (conditionObj == null || ConditionOperator.isNotEqualType(conditionOperator)) {
            return Pair.of(conditionSql.toString(), values);
        }

        try {
            Collection<Field> fields = ReflectUtils.getClassBeanFieldFast(conditionObj.getClass());
            for (Field field : fields) {
                Object value = field.get(conditionObj);
                if (value == null) {
                    continue;
                }

                conditionSql.append(NamingUtils.camel2Hung(field.getName()));
                conditionSql.append(conditionOperator.getOperator());
                conditionSql.append(PLACE_HOLDER);
                conditionSql.append(AND_STR);

                // 日期要格式化
                String type = field.getType().getSimpleName();
                checkFieldType(type);
                if (type.equals("Date")) {
                    values.put(index++, DateUtils.format((Date) value, DateUtils.SETTLE_PATTERN));
                } else {
                    values.put(index++, value.toString());
                }
            }
        } catch (Exception e) {
            throw new Bean2SqlException(e);
        }

        if (conditionSql.length() > 0) {
            int pos = conditionSql.lastIndexOf(AND_STR);
            conditionSql.delete(pos, pos + AND_STR.length());
        }

        return Pair.of(conditionSql.toString(), values);
    }

    public static void checkFieldType(String type) {
        Preconditions.checkArgument(
                "Long".equals(type) || "Integer".equals(type) ||
                "Float".equals(type) || "Double".equals(type) ||
                "Date".equals(type) || "String".equals(type));
    }
}
