package com.github.panhongan.mysql.conveyer.bean2sql.condition;

import com.github.panhongan.mysql.conveyer.bean2sql.condition.impl.AndCondition;
import com.github.panhongan.mysql.conveyer.bean2sql.condition.impl.BetweenAndCondition;
import com.github.panhongan.mysql.conveyer.bean2sql.condition.impl.EqualCondition;
import com.github.panhongan.mysql.conveyer.bean2sql.condition.impl.GreaterCondition;
import com.github.panhongan.mysql.conveyer.bean2sql.condition.impl.GreaterOrEqualCondition;
import com.github.panhongan.mysql.conveyer.bean2sql.condition.impl.LessCondition;
import com.github.panhongan.mysql.conveyer.bean2sql.condition.impl.LessOrEqualCondition;
import com.github.panhongan.mysql.conveyer.bean2sql.condition.impl.LikeCondition;
import com.github.panhongan.mysql.conveyer.bean2sql.condition.impl.NotEqualCondition;
import com.github.panhongan.mysql.conveyer.bean2sql.condition.impl.OrCondition;

/**
 * @author panhongan
 * @since 2019.7.8
 * @version 1.0
 */

public class SqlConditionMaker {

    public static <T> EqualCondition<T> equalCondition(T obj) {
        EqualCondition<T> condition = new EqualCondition<>();
        condition.setObj(obj);
        return condition;
    }

    public static <T> NotEqualCondition<T> notEqualCondition(T obj) {
        NotEqualCondition<T> condition = new NotEqualCondition<>();
        condition.setObj(obj);
        return condition;
    }

    public static <T> GreaterCondition<T> greaterCondition(T obj) {
        GreaterCondition<T> condition = new GreaterCondition<>();
        condition.setObj(obj);
        return condition;
    }

    public static <T> GreaterOrEqualCondition<T> greaterOrEqualCondition(T obj) {
        GreaterOrEqualCondition<T> condition = new GreaterOrEqualCondition<>();
        condition.setObj(obj);
        return condition;
    }

    public static <T> LessCondition<T> lessCondition(T obj) {
        LessCondition<T> condition = new LessCondition<>();
        condition.setObj(obj);
        return condition;
    }

    public static <T> LessOrEqualCondition<T> lessOrEqualCondition(T obj) {
        LessOrEqualCondition<T> condition = new LessOrEqualCondition<>();
        condition.setObj(obj);
        return condition;
    }

    public static <T> LikeCondition<T> likeCondition(T obj) {
        LikeCondition<T> condition = new LikeCondition<>();
        condition.setObj(obj);
        return condition;
    }

    public static AndCondition andCondition() {
        return new AndCondition();
    }

    public static OrCondition orCondition() {
        return new OrCondition();
    }

    public static <T extends Comparable<T>> BetweenAndCondition betweenAndCondition(String fieldName, T begin, T end) {
        BetweenAndCondition condition = new BetweenAndCondition();
        condition.setBegin(begin);
        condition.setEnd(end);
        condition.setFieldName(fieldName);
        return condition;
    }
}
