package com.github.panhongan.bean2sql.condition.sql;

import com.github.panhongan.bean2sql.condition.SqlConditionOperator;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.Map;

/**
 * @author panhongan
 * @since 2019.7.8
 * @version 1.0
 */

public interface SqlCondition {

    Pair<String, Map<Integer, String>> EMPTY_CONDITION_SQL = Pair.of("", Collections.emptyMap());

    SqlConditionOperator getConditionOperator();

    /**
     * Pair.left : PreparedStatement sql
     * Pair.Right:
     *              map.key : pos, eg : 1
     *              map.value : value at pos, eg : 1000
     *
     *  eg:
     *  select name, age from t_person where name = ? and age &gt; ?
     *  condition value : (1, "hello"), (2, 20)
     *
     *
     * @return Pair
     */
    Pair<String, Map<Integer, String>> conditionSql();
}
