package com.github.panhongan.mysql.conveyer.bean2sql.condition.impl;

import com.github.panhongan.mysql.conveyer.bean2sql.condition.SqlConditionOperator;

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
