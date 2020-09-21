package com.github.panhongan.bean2sql.condition.sql;

import com.github.panhongan.bean2sql.condition.SqlConditionOperator;

/**
 * @author panhongan
 * @since 2019.7.10
 * @version 1.0
 */

public class OrCondition extends AbstractCombineCondition {

    @Override
    public SqlConditionOperator getConditionOperator() {
        return SqlConditionOperator.OR;
    }
}
