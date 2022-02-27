package com.github.panhongan.commons;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author panhongan
 * @since 2019.7.13
 * @version 1.0
 */

@Getter
@Setter
@ToString
public class PageResult<T> implements Serializable {

    private int pageSize;

    private int currPage;

    private int totalPage;

    private int totalCount;

    private List<T> result = new ArrayList<>();

    public void add(T t) {
        if (t != null) {
            result.add(t);
        }
    }

    public void addAll(Collection<T> c) {
        if (CollectionUtils.isNotEmpty(c)) {
            result.addAll(c);
        }
    }

    public boolean isEmpty() {
        return result.isEmpty();
    }

    public boolean isNotEmpty() {
        return !result.isEmpty();
    }
}
