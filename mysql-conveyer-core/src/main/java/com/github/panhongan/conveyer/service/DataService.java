package com.github.panhongan.conveyer.service;

import com.github.panhongan.conveyer.service.req.AddReq;
import com.github.panhongan.conveyer.service.req.ModifyReq;

/**
 * @author panhongan
 * @since 2020.9.1
 * @version 1.0
 *
 * @param <B> biz object
 * @param <D> data object
 */

public interface DataService<B, D> {

    long add(AddReq<B> request);

    int modify(ModifyReq<B> request);

    int deleteById(long id);

    WriteOpChecker<B> getWriteOpChecker();
}
