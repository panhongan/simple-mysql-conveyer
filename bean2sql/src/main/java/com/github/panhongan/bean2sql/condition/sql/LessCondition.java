package com.github.panhongan.bean2sql.condition.sql;

import com.github.panhongan.bean2sql.condition.ConditionOperator;

/**
 * 示例：
 * obj.f1 = 100
 * obj.f2 = 20
 *
 * sql : f1 < ? and f2 < ?
 * vaues: (1, 100), (2, 20)
 *
 * @param <T> 包含小于字段的对象
 * @author panhongan
 * @since 2019.7.10
 * @version 1.0
 */

public class LessCondition<T> extends AbstractComparableCondition<T> {

    @Override
    public ConditionOperator getConditionOperator() {
        return ConditionOperator.LESS;
    }
}
