package com.github.panhongan.bean2sql.condition.impl;

import com.github.panhongan.bean2sql.Bean2SqlUtils;
import com.github.panhongan.bean2sql.condition.SqlCondition;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 组合条件
 *
 * @author panhongan
 * @since 2019.7.8
 * @version 1.0
 */

public abstract class AbstractCombineCondition implements SqlCondition {

    private Collection<SqlCondition> conditions = new ArrayList<>();

    public AbstractCombineCondition add(SqlCondition sqlCondition) {
        if (sqlCondition != null) {
            conditions.add(sqlCondition);
        }
        return this;
    }

    @Override
    public Pair<String, Map<Integer, String>> conditionSql() {
        if (CollectionUtils.isEmpty(conditions)) {
            return EMPTY_CONDITION_SQL;
        }

        StringBuilder builder = new StringBuilder();
        Map<Integer, String> values = new HashMap<>();
        String conditionOperator = this.getConditionOperator().getOperator();

        for (SqlCondition condition : conditions) {
            Pair<String, Map<Integer, String>> sql = condition.conditionSql();
            if (StringUtils.isEmpty(sql.getLeft())) {
                continue;
            }

            builder.append(sql.getLeft());
            builder.append(conditionOperator);

            int oriSize = values.size();
            for (Map.Entry<Integer, String> entry : sql.getRight().entrySet()) {
                values.put(entry.getKey() + oriSize, entry.getValue());
            }
        }

        if (builder.length() > 0) {
            builder.delete(builder.length() - conditionOperator.length(), builder.length());
            builder.insert(0, Bean2SqlUtils.LEFT_BRACKET);
            builder.append(Bean2SqlUtils.RIGHT_BRACKET);
        }

        return Pair.of(builder.toString(), values);
    }
}
