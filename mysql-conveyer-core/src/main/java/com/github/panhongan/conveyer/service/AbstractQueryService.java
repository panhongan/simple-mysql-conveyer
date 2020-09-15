package com.github.panhongan.conveyer.service;

import com.github.panhongan.bean2sql.table.PageContext;
import com.github.panhongan.bean2sql.table.TableAccess;
import com.github.panhongan.commons.DbBase;
import com.github.panhongan.commons.ExceptionalActionWrapper;
import com.github.panhongan.bean2sql.transaction.TransactionManagerEx;
import com.github.panhongan.commons.PageResult;
import com.github.panhongan.conveyer.service.req.QueryByConditionReq;
import com.github.panhongan.conveyer.service.req.QueryByPageReq;
import com.github.panhongan.utils.object.ObjectUtils;
import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author panhongan
 * @since 2020.9.2
 * @version 1.0
 */

@Slf4j
@Getter
@Setter
@Service
public abstract class AbstractQueryService<B, D extends DbBase> {

    protected abstract TableAccess<D> getTableAccess();

    protected abstract Converter<B, D> getConverter();

    @Autowired
    private TransactionManagerEx transactionManagerEx;

    public B queryById(long id) {
        return ExceptionalActionWrapper.run(() -> {
            D condition = this.getConverter().emptyDO();
            condition.setId(id);
            List<D> doObjs = this.getTableAccess().queryByCondition(condition);
            Preconditions.checkArgument(CollectionUtils.size(doObjs) == 1, "记录条数不为1， id=" + id);
            return this.getConverter().do2bo(doObjs.get(0));
        });
    }

    public List<B> queryByCondition(QueryByConditionReq<B> request) {
        return ExceptionalActionWrapper.run(() -> {
            ObjectUtils.validateObject(request);
            D condition = this.getConverter().bo2do(request.getBizObjCondition());
            if (condition == null) {
                condition = this.getConverter().emptyDO();
            }

            List<D> doObjs = this.getTableAccess().queryByCondition(condition, request.getSqlCondition());
            return this.getConverter().dos2bos(doObjs);
        });
    }

    public PageResult<B> queryByPage(QueryByPageReq<B> request) {
        return ExceptionalActionWrapper.run(() -> {
            ObjectUtils.validateObject(request);

            D condition = this.getConverter().bo2do(request.getBizObjCondition());
            if (condition == null) {
                condition = this.getConverter().emptyDO();
            }

            PageContext pageContext = new PageContext();
            pageContext.setCurrPage(request.getCurrPage());
            pageContext.setPageSize(request.getPageSize());

            PageResult<D> pageResult = this.getTableAccess().queryByPage(condition, request.getSqlCondition(), pageContext);

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
}
