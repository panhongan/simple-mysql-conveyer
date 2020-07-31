package com.github.panhongan.common.page;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author panhongan
 * @since 2019.7.13
 * @version 1.0
 */

@Getter
@Setter
@ToString
public class PageContext implements Serializable {

    /**
     * 当前查询的分页
     */
    private int currPage;

    /**
     * 分页的大小
     */
    private int pageSize;
}
