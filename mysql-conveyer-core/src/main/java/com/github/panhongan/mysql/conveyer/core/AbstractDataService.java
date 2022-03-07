package com.github.panhongan.mysql.conveyer.core;

import com.github.panhongan.mysql.conveyer.bean2sql.table.PageContext;
import com.github.panhongan.mysql.conveyer.bean2sql.table.TableAccess;
import com.github.panhongan.mysql.conveyer.commons.TransactionManagerEx;
import com.github.panhongan.mysql.conveyer.commons.DbBase;
import com.github.panhongan.mysql.conveyer.commons.ExceptionalActionWrapper;
import com.github.panhongan.mysql.conveyer.commons.PageResult;
import com.github.panhongan.mysql.conveyer.core.req.AddReq;
import com.github.panhongan.mysql.conveyer.core.req.ModifyReq;
import com.github.panhongan.mysql.conveyer.core.req.QueryByConditionReq;
import com.github.panhongan.mysql.conveyer.core.req.QueryByPageReq;
import com.github.panhongan.utils.object.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author panhongan
 * @since 2020.9.2
 * @version 1.0
 */

@Service
public abstract class AbstractDataService<B, D> {

    @Autowired
    private TransactionManagerEx transactionManagerEx;

    protected abstract TableAccess<D> getTableAccess();

    protected abstract Converter<B, D> getConverter();

    protected abstract WriteChecker<B, D> getWriteChecker();

    public Optional<B> queryById(long id) {
        return ExceptionalActionWrapper.run(() -> {
            Optional<D> doObj = this.getTableAccess().queryById(id);
            return doObj.map(x -> this.getConverter().do2bo(x));
        });
    }

    public List<B> queryByCondition(QueryByConditionReq<B> request) {
        return ExceptionalActionWrapper.run(() -> {
            ObjectUtils.validateObject(request);
            D condition = this.getConverter().bo2do(request.getBizObjCondition());
            if (Objects.isNull(condition)) {
                condition = this.getTableAccess().emptyDO();
            }

            List<D> doObjList = this.getTableAccess().queryByCondition(condition, request.getSqlCondition(), request.getOrderBy());
            return this.getConverter().dos2bos(doObjList);
        });
    }

    public PageResult<B> queryByPage(QueryByPageReq<B> request) {
        return ExceptionalActionWrapper.run(() -> {
            ObjectUtils.validateObject(request);

            D condition = this.getConverter().bo2do(request.getBizObjCondition());
            if (condition == null) {
                condition = this.getTableAccess().emptyDO();
            }

            PageContext pageContext = new PageContext();
            pageContext.setCurrPage(request.getCurrPage());
            pageContext.setPageSize(request.getPageSize());

            PageResult<D> pageResult = this.getTableAccess().queryByPage(condition, request.getSqlCondition(),request.getOrderBy(), pageContext);

            // convert
            PageResult<B> finalResult = new PageResult<>();
            finalResult.setTotalCount(pageResult.getTotalCount());
            finalResult.setTotalPage(pageResult.getTotalPage());
            finalResult.setPageSize(pageResult.getPageSize());
            finalResult.setCurrPage(pageResult.getCurrPage());
            for (D doObj : pageResult.getResult()) {
                finalResult.add(this.getConverter().do2bo(doObj));
            }
            return finalResult;
        });
    }

    public long add(AddReq<B> request) {
        return ExceptionalActionWrapper.run(() -> {
            ObjectUtils.validateObject(request);
            B bizObj = request.getBizObj();
            ObjectUtils.validateObject(bizObj);

            // check write operation
            this.getWriteChecker().checkBeforeAdd(bizObj);

            D doObj = this.getConverter().bo2do(bizObj);

            if (doObj instanceof DbBase) {
                Date currTimestamp = new Date();
                String operator = StringUtils.isNotEmpty(request.getCreatedBy()) ? request.getCreatedBy() : DbBase.defaultOperator();

                DbBase dbBase = (DbBase) doObj;
                dbBase.setCreatedAt(currTimestamp);
                dbBase.setCreatedBy(operator);
                dbBase.setUpdatedAt(currTimestamp);
                dbBase.setUpdatedBy(operator);
            }

            return transactionManagerEx.execute(() -> this.getTableAccess().insert(doObj));
        });
    }

    public int modify(ModifyReq<B> request) {
        return ExceptionalActionWrapper.run(() -> {
            ObjectUtils.validateObject(request);
            B newBizObj = request.getNewBizObj();
            ObjectUtils.validateObject(newBizObj);

            long oriId = request.getOriId();

            // check new data
            this.getWriteChecker().checkBeforeModify(oriId, newBizObj);

            // modify data
            D newObj = this.getConverter().bo2do(newBizObj);

            if (newObj instanceof DbBase) {
                Date currTimestamp = new Date();
                String operator = StringUtils.isNotEmpty(request.getUpdatedBy()) ? request.getUpdatedBy() : DbBase.defaultOperator();

                DbBase dbBase = (DbBase) newObj;
                dbBase.setUpdatedAt(currTimestamp);
                dbBase.setUpdatedBy(operator);
            }

            return transactionManagerEx.execute(() -> this.getTableAccess().update(oriId, newObj));
        });
    }

    public int deleteById(long id) {
        return ExceptionalActionWrapper.run(() -> {
            this.getWriteChecker().checkBeforeDelete(id);
            return transactionManagerEx.execute(() -> this.getTableAccess().deleteById(id));
        });
    }
}
