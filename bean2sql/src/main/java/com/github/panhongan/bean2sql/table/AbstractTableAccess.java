package com.github.panhongan.bean2sql.table;

import com.github.panhongan.bean2sql.SqlMaker;
import com.github.panhongan.bean2sql.condition.SqlConditionMaker;
import com.github.panhongan.commons.MysqlConveyerException;
import com.github.panhongan.commons.PageResult;
import com.github.panhongan.utils.object.ObjectUtils;
import com.github.panhongan.bean2sql.condition.impl.AndCondition;
import com.github.panhongan.bean2sql.condition.impl.EqualCondition;
import com.github.panhongan.bean2sql.condition.SqlCondition;
import lombok.extern.slf4j.Slf4j;
import net.sf.oval.constraint.NotNull;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author panhongan
 * @since 2019.7.14
 * @version 1.0
 */

@Slf4j
public abstract class AbstractTableAccess<D> implements TableAccess<D> {

    @Autowired
    private SqlExecutor sqlExecutor;

    @Override
    public long getMaxRowId(SqlCondition sqlCondition) throws MysqlConveyerException {
        Pair<String, Map<Integer, String>> pair = SqlMaker.makeMaxRowIdSql(this.getTable(), sqlCondition);
        log.info("getMaxRowId, sql = {}, values = {}", pair.getLeft(), pair.getRight());
        return sqlExecutor.getMaxRowId(pair.getLeft(), pair.getRight());
    }

    @Override
    public long insert(@NotNull D record) throws MysqlConveyerException {
        ObjectUtils.validateObject(record);

        Pair<String, Map<Integer, String>> pair = SqlMaker.makeInsertSql(this.getTable(), record);
        log.info("insert, sql = {}, values = {}", pair.getLeft(), pair.getRight());
        return sqlExecutor.insert(pair.getLeft(), pair.getRight()).get(0);
    }

    @Override
    public int deleteById(long id) throws MysqlConveyerException {
        Pair<String, Map<Integer, String>> pair = SqlMaker.makeDeleteByIdSql(this.getTable(), id);
        log.info("deleteById, sql = {}, values = {}", pair.getLeft(), pair.getRight());
        return sqlExecutor.update(pair.getLeft(), pair.getRight());
    }

    @Override
    public int update(long id, @NotNull D newRecord) throws MysqlConveyerException {
        ObjectUtils.validateObject(newRecord);

        Pair<String, Map<Integer, String>> pair = SqlMaker.makeUpdateSql(this.getTable(), id, newRecord);
        log.info("update, sql = {}, values = {}", pair.getLeft(), pair.getRight());
        return sqlExecutor.update(pair.getLeft(), pair.getRight());
    }

    @Override
    public Optional<D> queryById(long id) throws MysqlConveyerException {
        D obj = this.emptyDO();
        Pair<String, Map<Integer, String>> pair = SqlMaker.makeQueryByIdSql(this.getTable(), id, obj.getClass());
        log.info("queryById, sql = {}, values = {}", pair.getLeft(), pair.getRight());
        List<D> list = sqlExecutor.select(pair.getLeft(), pair.getRight(), (Class<D>) obj.getClass());
        if (CollectionUtils.isNotEmpty(list)) {
            return Optional.of(list.get(0));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<D> queryByCondition(@NotNull D condition) throws MysqlConveyerException {
        ObjectUtils.validateObject(condition);
        return this.queryByCondition(condition, null);
    }

    @Override
    public List<D> queryByCondition(@NotNull D condition, SqlCondition sqlCondition) throws MysqlConveyerException {
        ObjectUtils.validateObject(condition);

        SqlCondition andCondition = this.makeAndCondition(condition, sqlCondition);
        return this.queryByCondition(andCondition, (Class<D>) condition.getClass());
    }

    @Override
    public List<D> queryByCondition(@NotNull SqlCondition sqlCondition, Class<D> c) throws MysqlConveyerException {
        ObjectUtils.validateObject(sqlCondition);

        Pair<String, Map<Integer, String>> pair = SqlMaker.makeQueryByConditionSql(this.getTable(), sqlCondition, c);
        log.info("queryByCondition, sql = {}, values = {}", pair.getLeft(), pair.getRight());
        return sqlExecutor.select(pair.getLeft(), pair.getRight(), c);
    }

    @Override
    public PageResult<D> queryByPage(@NotNull D condition, @NotNull PageContext pageContext) throws MysqlConveyerException {
        ObjectUtils.validateObject(condition);
        ObjectUtils.validateObject(pageContext);
        return this.queryByPage(condition, null, pageContext);
    }

    @Override
    public PageResult<D> queryByPage(@NotNull D condition, SqlCondition sqlCondition, @NotNull PageContext pageContext) throws MysqlConveyerException {
        ObjectUtils.validateObject(condition);
        ObjectUtils.validateObject(pageContext);
        SqlCondition andCondition = this.makeAndCondition(condition, sqlCondition);
        return this.queryByPage(andCondition, pageContext, (Class<D>) condition.getClass());
    }

    @Override
    public PageResult<D> queryByPage(@NotNull SqlCondition sqlCondition, @NotNull PageContext pageContext, Class<D> c) throws MysqlConveyerException {
        ObjectUtils.validateObject(sqlCondition);
        ObjectUtils.validateObject(pageContext);

        Pair<Pair<String, String>, Map<Integer, String>> pair = SqlMaker.makeQueryByPageSql(this.getTable(), sqlCondition, pageContext, c);
        String countSql = pair.getLeft().getLeft();
        String pageSql = pair.getLeft().getRight();

        // total count
        log.info("queryByPage, count sql = {}, values = {}", countSql, pair.getRight());
        int totalCount = sqlExecutor.getCount(countSql, pair.getRight());
        int totalPage = totalCount / pageContext.getPageSize() + (totalCount % pageContext.getPageSize() > 0 ? 1 : 0);

        // page data
        log.info("queryByPage, page sql = {}, values = {}", pageSql, pair.getRight());
        Collection<D> rows = sqlExecutor.select(pageSql, pair.getRight(), c);

        // page result
        PageResult<D> pageResult = new PageResult<>();
        pageResult.setCurrPage(pageContext.getCurrPage());
        pageResult.setPageSize(pageContext.getPageSize());
        pageResult.setTotalPage(totalPage);
        pageResult.setTotalCount(totalCount);
        pageResult.addAll(rows);
        return pageResult;
    }

    protected SqlCondition makeAndCondition(D condition, SqlCondition sqlCondition) {
        EqualCondition equalCondition = SqlConditionMaker.equalCondition(condition);
        AndCondition andCondition = SqlConditionMaker.andCondition();
        andCondition.add(equalCondition).add(sqlCondition);
        return andCondition;
    }
}
