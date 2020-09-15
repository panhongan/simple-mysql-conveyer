package com.github.panhongan.bean2sql.condition.sql;

import com.github.panhongan.bean2sql.condition.Bean2SqlUtils;
import com.github.panhongan.bean2sql.condition.ConditionOperator;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

/**
 * between的范围必须是comparable
 *
 * condition sql is : where ? between ? and ?
 * values: (1, "hello"), (2, 100), (3, 200)
 *
 * @param <T> 任意类型的对象
 * @author panhongan
 * @since 2019.7.10
 * @version 1.0
 */

@Builder
@Getter
@ToString
public class BetweenAndCondition<T extends Comparable<T>> implements SqlCondition {

    private String fieldName;

    private T begin;

    private T end;

    @Override
    public ConditionOperator getConditionOperator() {
        return ConditionOperator.BETWEEN;
    }

    @Override
    public Pair<String, Map<Integer, String>> conditionSql() {
        return Bean2SqlUtils.getBetweenAndConditionSql(fieldName, begin, end);
    }
}
