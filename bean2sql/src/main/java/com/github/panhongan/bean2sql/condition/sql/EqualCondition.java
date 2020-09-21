package com.github.panhongan.bean2sql.condition.sql;

import com.github.panhongan.bean2sql.condition.Bean2SqlUtils;
import com.github.panhongan.bean2sql.condition.SqlConditionOperator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

/**
 * 相等条件通过对象来表达，对象字段类型必须是：Long, Integer, Float, Double, String, Date
 * 后续可支持其他类型
 *
 * 如果对象obj有两个字段: f1 and f2
 * obj.f1 = "hello"
 * obj.f2 = 100
 *
 * condition sql is : f1 = ? and f2 = ?
 * values: (1, "hello"), (2, 100)
 *
 * @param <T> 任意类型的对象
 * @author panhongan
 * @since 2019.7.10
 * @version 1.0
 */

@Getter
@Setter
@ToString
public class EqualCondition<T> implements SqlCondition {

    private T obj;

    @Override
    public SqlConditionOperator getConditionOperator() {
        return SqlConditionOperator.EQUAL;
    }

    @Override
    public Pair<String, Map<Integer, String>> conditionSql() {
        return Bean2SqlUtils.getEqualTypeConditionSql(obj, this.getConditionOperator());
    }
}
