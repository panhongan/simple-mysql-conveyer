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

    void checkBeforeAdd(B bizObj) throws MysqlConveyerException;

    default void checkBeforeModify(long oriId, B newBizObj) throws MysqlConveyerException {
        Optional<D> optional = this.getTableAccess().queryById(oriId);
        if (!optional.isPresent()) {
            throw new MysqlConveyerException("待修改记录不存在, id=" + oriId);
        }

        this.checkModifiedObj(optional.get(), newBizObj);
    }

    default void checkBeforeDelete(long oriId) throws MysqlConveyerException {
        Optional<D> optional = this.getTableAccess().queryById(oriId);
        if (!optional.isPresent()) {
            throw new MysqlConveyerException("待删除记录不存在, id=" + oriId);
        }
    }

    default void checkModifiedObj(D oldObj, B newBizObj) throws MysqlConveyerException { }
}
