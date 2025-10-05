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
        this.checkUniq(bizObj);
    }

    default void checkBeforeModify(long oriId, B newBizObj) throws MysqlConveyerException {
        this.checkParametersWhenUpdate(newBizObj);

        Optional<D> optional = this.getTableAccess().queryById(oriId);
        if (!optional.isPresent()) {
            throw new MysqlConveyerException("待修改记录不存在, id=" + oriId);
        }

        B oldBizObj = this.getConverter().do2bo(optional.get());

        B mergedObj = this.mergeObjectWhenUpdate(oldBizObj, newBizObj);
        this.checkUniq(mergedObj);

        this.checkMoreWhenUpdate(oldBizObj, newBizObj);
    }

    default void checkBeforeDelete(long oriId) throws MysqlConveyerException {
        Optional<D> optional = this.getTableAccess().queryById(oriId);
        if (!optional.isPresent()) {
            throw new MysqlConveyerException("待删除记录不存在, id=" + oriId);
        }
    }

    default void checkParametersWhenAdd(B bizObj) throws MysqlConveyerException { }

    default void checkParametersWhenUpdate(B newBizObj) throws MysqlConveyerException { }

    B mergeObjectWhenUpdate(B oldBizObj, B newBizObj) throws MysqlConveyerException;

    default void checkUniq(B bizObj) throws MysqlConveyerException { }

    default void checkMoreWhenUpdate(B oldBizObj, B newBizObj) throws MysqlConveyerException { }
}
