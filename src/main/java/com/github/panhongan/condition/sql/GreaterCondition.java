package com.github.panhongan.condition.sql;

import com.github.panhongan.condition.ConditionOperator;
import lombok.Builder;

/**
 * 示例：
 * obj.f1 = 100
 * obj.f2 = 20
 *
 * sql : f1 > ? and f2 > ?
 * vaues: (1, 100), (2, 20)
 * 
 * @param <T>
 * @author panhongan
 * @since 2019.7.10
 * @version 1.0
 */

@Builder
public class GreaterCondition<T> extends AbstractComparableCondition {

    @Override
    public ConditionOperator getConditionOperator() {
        return ConditionOperator.GREATER;
    }
}
