package com.github.panhongan.mysql.conveyer.core;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author panhongan
 * @since 2020.9.1
 * @version 1.0
 *
 * @param <B> biz object
 * @param <D> data object
 */

public interface Converter<B, D> {

    B do2bo(D doObj);

    D bo2do(B bizObj);

    default List<B> dos2bos(List<D> doObjList) {
        List<B> bizObjList = new ArrayList<>();

        if (CollectionUtils.isEmpty(doObjList)) {
            return bizObjList;
        }

        for (D doObj : doObjList) {
            bizObjList.add(this.do2bo(doObj));
        }
        return bizObjList;
    }

    default List<D> bos2dos(List<B> bizObjList) {
        List<D> doObjList = new ArrayList<>();

        if (CollectionUtils.isEmpty(bizObjList)) {
            return doObjList;
        }

        for (B bizObj : bizObjList) {
            doObjList.add(this.bo2do(bizObj));
        }
        return doObjList;
    }
}
