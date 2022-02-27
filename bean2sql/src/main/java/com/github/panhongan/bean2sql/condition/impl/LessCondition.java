package com.github.panhongan.bean2sql.condition.impl;

import com.github.panhongan.bean2sql.condition.SqlConditionOperator;

/**
 * 示例：
 * obj.f1 = 100
 * obj.f2 = 20
 *
 * sql : f1 &lt; ? and f2 &lt; ?
 * values: (1, 100), (2, 20)
 *
 * @param <T> 包含小于字段的对象
 * @author panhongan
 * @since 2019.7.10
 * @version 1.0
 */

public class LessCondition<T> extends AbstractComparableCondition<T> {

    @Override
    public SqlConditionOperator getConditionOperator() {
        return SqlConditionOperator.LESS;
    }
}
