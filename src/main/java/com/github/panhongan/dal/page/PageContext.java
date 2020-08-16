package com.github.panhongan.dal.page;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.sf.oval.constraint.Max;
import net.sf.oval.constraint.Min;

/**
 * @author panhongan
 * @since 2019.7.13
 * @version 1.0
 */

@Getter
@Setter
@ToString
public class PageContext {

    /**
     * 当前查询的分页
     */
    @Min(value = 1, message = "最小页为1")
    private int currPage;

    /**
     * 分页的大小
     */
    @Max(value = 1000, message = "分页最大值为1000")
    @Min(value = 1, message = "分页最小值为1")
    private int pageSize;
}
