package com.github.panhongan.conveyer.service;

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

    D emptyDO();

    default List<B> dos2bos(List<D> doObjs) {
        List<B> bizObjs = new ArrayList<>();

        if (CollectionUtils.isEmpty(doObjs)) {
            return bizObjs;
        }

        for (D doObj : doObjs) {
            bizObjs.add(this.do2bo(doObj));
        }
        return bizObjs;
    }

    default List<D> bos2dos(List<B> bizObjs) {
        List<D> doObjs = new ArrayList<>();

        if (CollectionUtils.isEmpty(bizObjs)) {
            return doObjs;
        }

        for (B bizObj : bizObjs) {
            doObjs.add(this.bo2do(bizObj));
        }
        return doObjs;
    }
}
