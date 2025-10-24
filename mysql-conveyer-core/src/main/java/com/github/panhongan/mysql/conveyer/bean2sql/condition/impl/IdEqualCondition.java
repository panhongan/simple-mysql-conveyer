package com.github.panhongan.mysql.conveyer.bean2sql.condition.impl;

import com.github.panhongan.mysql.conveyer.bean2sql.condition.SqlCondition;
import com.github.panhongan.mysql.conveyer.bean2sql.condition.SqlConditionOperator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.Map;

/**
 * condition sql is : id = 1
 *
 * @author panhongan
 * @since 2019.7.10
 * @version 1.0
 */

@Getter
@Setter
@ToString
public class IdEqualCondition implements SqlCondition {

    private long id;

    public IdEqualCondition(long id) {
        this.id = id;
    }

    @Override
    public SqlConditionOperator getConditionOperator() {
        return SqlConditionOperator.EQUAL;
    }

    @Override
    public Pair<String, Map<Integer, String>> conditionSql() {
        return Pair.of(String.format("id = %d", id), Collections.emptyMap());
    }
}
