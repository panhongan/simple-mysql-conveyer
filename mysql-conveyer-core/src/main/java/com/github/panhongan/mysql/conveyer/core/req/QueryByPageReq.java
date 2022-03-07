package com.github.panhongan.mysql.conveyer.core.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.sf.oval.constraint.Max;
import net.sf.oval.constraint.Min;

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
public class QueryByPageReq<B> extends QueryByConditionReq<B> {

    @Min(value = 1, message = "页数最小值为1")
    private int currPage;

    @Max(value = 1000, message = "分页最大值为1000")
    @Min(value = 1, message = "分页最小值为1")
    private int pageSize;
}
