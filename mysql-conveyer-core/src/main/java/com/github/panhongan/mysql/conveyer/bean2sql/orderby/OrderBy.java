package com.github.panhongan.mysql.conveyer.bean2sql.orderby;

import com.github.panhongan.mysql.conveyer.bean2sql.Bean2SqlUtils;

public class OrderBy<T> {

    private Class<T> orderByClass;

    private boolean ascending = true;

    public OrderBy(Class<T> orderByClass) {
        this(orderByClass, true);
    }

    public OrderBy(Class<T> orderByClass, boolean ascending) {
        this.orderByClass = orderByClass;
        this.ascending = ascending;
    }

    public String toOrderBy() {
        return "order by " + Bean2SqlUtils.getSelectFieldsStringFast(orderByClass) + (ascending ? "" : " desc");
    }
}
