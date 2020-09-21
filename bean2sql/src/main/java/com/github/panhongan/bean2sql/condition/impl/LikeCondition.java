package com.github.panhongan.bean2sql.condition.impl;

import com.github.panhongan.bean2sql.Bean2SqlUtils;
import com.github.panhongan.bean2sql.condition.SqlCondition;
import com.github.panhongan.bean2sql.condition.SqlConditionOperator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

/**
 *
 * obj对象有String类型两个字段f1和f2, 且取值分别为val和val2(都不为null且不能为空字符串)
 * 对应的sql条件表达式: f1 like '%val1%' and f2 like '%val2%'
 *
 *
 * @author panhongan
 * @since 2020.9.10
 * @version 1.0
 *
 * @param <T> obj对象里不为null的String/Date类型字段都是like
 *
 */

@Getter
@Setter
@ToString
public class LikeCondition<T> implements SqlCondition {

    private T obj;

    @Override
    public SqlConditionOperator getConditionOperator() {
        return SqlConditionOperator.LIKE;
    }

    @Override
    public Pair<String, Map<Integer, String>> conditionSql() {
        return Bean2SqlUtils.getLikeConditionSql(obj);
    }
}
