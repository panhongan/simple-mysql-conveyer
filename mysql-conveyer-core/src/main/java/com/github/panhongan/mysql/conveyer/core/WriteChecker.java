package com.github.panhongan.mysql.conveyer.core;

import com.github.panhongan.mysql.conveyer.bean2sql.table.TableAccess;
import com.github.panhongan.mysql.conveyer.commons.MysqlConveyerException;

import java.util.Optional;

/**
 * @author panhongan
 * @since 2020.9.10
 * @version 1.0
 *
 * @param <B> biz object
 */

public interface WriteChecker<B, D> {

    TableAccess<D> getTableAccess();

    Converter<B, D> getConverter();

    default void checkBeforeAdd(B bizObj) throws MysqlConveyerException {
        this.checkParametersWhenAdd(bizObj);
        this.checkUniq(-1L, bizObj);
    }

    default void checkBeforeModify(long oriId, B newBizObj) throws MysqlConveyerException {
        this.checkParametersWhenUpdate(newBizObj);
        this.checkExists(oriId);
        this.checkUniq(oriId, newBizObj);

        this.checkMoreWhenUpdate(newBizObj, newBizObj);
    }

    default void checkBeforeDelete(long oriId) throws MysqlConveyerException {
        this.checkExists(oriId);
    }

    default void checkExists(long oriId) throws MysqlConveyerException {
         Optional<D> optional = this.getTableAccess().queryById(oriId);
         if (!optional.isPresent()) {
             throw new MysqlConveyerException("待删除记录不存在, id=" + oriId);
         }
     }

    default void checkParametersWhenAdd(B bizObj) throws MysqlConveyerException { }

    default void checkParametersWhenUpdate(B newBizObj) throws MysqlConveyerException { }

    default void checkUniq(long oriId, B bizObj) throws MysqlConveyerException { }

    default void checkMoreWhenUpdate(B oldBizObj, B newBizObj) throws MysqlConveyerException { }
}
