package com.github.panhongan.conveyer.service;

import com.github.panhongan.commons.MysqlConveyerException;

/**
 * @author panhongan
 * @since 2020.9.10
 * @version 1.0
 *
 * @param <B> biz object
 */

public interface WriteOpChecker<B> {

    void checkBeforeAdd(B bizObj) throws MysqlConveyerException;

    void checkBeforeModify(long oriId, B newBizObj) throws MysqlConveyerException;

    void checkBeforeDelete(long oriId) throws MysqlConveyerException;
}
