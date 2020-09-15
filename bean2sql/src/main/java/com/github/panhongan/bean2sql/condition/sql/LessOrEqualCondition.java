package com.github.panhongan.bean2sql.condition.sql;

import com.github.panhongan.bean2sql.condition.ConditionOperator;
import lombok.Builder;

/**
 * 示例：
 * obj.f1 = 100
 * obj.f2 = 20
 *
 * sql : f1 <= ? and f2 <= ?
 * vaues: (1, 100), (2, 20)
 *
 * @param <T> 包含小于或等于字段的对象
 */

public class LessOrEqualCondition<T> extends AbstractComparableCondition<T> {

    @Override
    public ConditionOperator getConditionOperator() {
        return ConditionOperator.LESS_OR_EQUAL;
    }
}
