package com.github.panhongan.condition.sql;

import com.github.panhongan.condition.Bean2SqlUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

/**
 * 对象字段类型要求为：Integer, Long, Short, Float, Double, Date
 *
 * @param <T>
 * @author panhongan
 * @since 2019.7.10
 * @version 1.0
 */

@Getter
@Setter
@ToString
public abstract class AbstractComparableCondition<T> implements SqlCondition {

    private T obj;

    @Override
    public Pair<String, Map<Integer, String>> conditionSql() {
        return Bean2SqlUtils.getComparableConditionSql(obj, this.getConditionOperator());
    }
}
