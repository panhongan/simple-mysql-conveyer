package com.github.panhongan.conveyer.service.req;

import com.github.panhongan.bean2sql.condition.sql.SqlCondition;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.sf.oval.constraint.NotNull;

/**
 * @author panhongan
 * @since 2020.9.1
 * @version 1.0
 *
 * @param <B> 业务对象
 */

@Getter
@Setter
@ToString
public class QueryByConditionReq<B> {

    @NotNull
    private B bizObjCondition;

    private SqlCondition sqlCondition;
}
