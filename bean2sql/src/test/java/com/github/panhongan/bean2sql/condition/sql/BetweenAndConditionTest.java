package com.github.panhongan.bean2sql.condition.sql;

import com.github.panhongan.bean2sql.condition.ConditionMaker;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.Date;
import java.util.Map;

/**
 * @author panhongan
 * @since 2020.9.15
 * @version 1.0
 */

public class BetweenAndConditionTest {

    @Test
    public void testConditionSql_Ok() {
        BetweenAndCondition betweenAndCondition = ConditionMaker.betweenAndCondition("create_date", new Date(), new Date());
        Pair<String, Map<Integer, String>> pair = betweenAndCondition.conditionSql();
        assert(pair.getLeft().equals("(create_date between ? and ?)"));
        assert(pair.getRight().size() == 2);
    }
}
