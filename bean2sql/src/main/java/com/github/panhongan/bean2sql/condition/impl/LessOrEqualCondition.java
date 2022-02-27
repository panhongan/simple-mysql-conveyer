package com.github.panhongan.bean2sql.condition.impl;

import com.github.panhongan.bean2sql.condition.SqlConditionOperator;

/**
 * 示例：
 * obj.f1 = 100
 * obj.f2 = 20
 *
 * sql : f1 &lt;= ? and f2 &lt;= ?
 * values: (1, 100), (2, 20)
 *
 * @param <T> 包含小于或等于字段的对象
 */

public class LessOrEqualCondition<T> extends AbstractComparableCondition<T> {

    @Override
    public SqlConditionOperator getConditionOperator() {
        return SqlConditionOperator.LESS_OR_EQUAL;
    }
}
