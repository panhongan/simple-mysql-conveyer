package com.github.panhongan.conveyer.service;

import com.github.panhongan.bean2sql.transaction.TransactionManagerEx;
import com.github.panhongan.commons.DbBase;
import com.github.panhongan.commons.ExceptionalActionWrapper;
import com.github.panhongan.conveyer.service.req.AddReq;
import com.github.panhongan.conveyer.service.req.ModifyReq;
import com.github.panhongan.utils.object.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author panhongan
 * @since 2020.9.2
 * @version 1.0
 */

@Service
public abstract class AbstractDataService<B, D extends DbBase>
        extends AbstractQueryService<B, D>
        implements DataService<B, D> {

    @Autowired
    private TransactionManagerEx transactionManagerEx;

    @Override
    public long add(AddReq<B> request) {
        return ExceptionalActionWrapper.run(() -> {
            ObjectUtils.validateObject(request);
            B bizObj = request.getBizObj();
            ObjectUtils.validateObject(bizObj);

            // check write operation
            this.getWriteOpChecker().checkBeforeAdd(bizObj);

            // insert data
            D doObj = this.getConverter().bo2do(bizObj);
            doObj.setCreatedAt(new Date());
            doObj.setCreatedBy(request.getCreatedBy());
            doObj.setUpdatedAt(new Date());
            doObj.setUpdatedBy(request.getCreatedBy());

            return transactionManagerEx.execute(() -> this.getTableAccess().insert(doObj));
        });
    }

    @Override
    public int modify(ModifyReq<B> request) {
        return ExceptionalActionWrapper.run(() -> {
            ObjectUtils.validateObject(request);
            B newBizObj = request.getNewBizObj();
            ObjectUtils.validateObject(newBizObj);

            long oriId = request.getOriId();

            // check new data
            this.getWriteOpChecker().checkBeforeModify(oriId, newBizObj);

            // modify data
            D newObj = this.getConverter().bo2do(newBizObj);
            newObj.setUpdatedAt(new Date());
            newObj.setUpdatedBy(request.getUpdatedBy());

            return transactionManagerEx.execute(() -> this.getTableAccess().update(oriId, newObj));
        });
    }

    @Override
    public int deleteById(long id) {
        return ExceptionalActionWrapper.run(() -> {
            this.getWriteOpChecker().checkBeforeDelete(id);
            return transactionManagerEx.execute(() -> this.getTableAccess().deleteById(id));
        });
    }
}
