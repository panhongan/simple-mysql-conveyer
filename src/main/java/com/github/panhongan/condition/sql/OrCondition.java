package com.github.panhongan.condition.sql;

import com.github.panhongan.condition.ConditionOperator;

/**
 * @author panhongan
 * @since 2019.7.10
 * @version 1.0
 */
public class OrCondition extends AbstractCombineCondition {

    @Override
    public ConditionOperator getConditionOperator() {
        return ConditionOperator.OR;
    }
}
