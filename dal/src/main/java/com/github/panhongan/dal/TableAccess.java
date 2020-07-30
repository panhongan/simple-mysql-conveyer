package com.github.panhongan.dal;

import com.github.panhongan.common.Bean2SqlException;
import com.github.panhongan.common.page.PageContext;
import com.github.panhongan.common.page.PageResult;
import com.github.panhongan.condition.sql.SqlCondition;
import net.sf.oval.constraint.NotNull;

import java.util.List;

/**
 * @param <D> Data Object mapping to db
 * @author panhongan
 * @since 2019.7.14
 * @version 1.0
 */

public interface TableAccess<D> {

    /**
     * 查询满足条件记录的最大ID
     *
     * @param sqlCondition
     * @return
     */
    long getMaxRowId(@NotNull SqlCondition sqlCondition);

    /**
     *
     * @param record
     * @return 插入数据后返回的记录id
     * @throws Bean2SqlException
     */
    long insert(@NotNull D record) throws Bean2SqlException;

    /**
     * @param id 待删除记录id
     * @return 删除生效的条数
     * @throws Bean2SqlException
     */
    int deleteById(long id) throws Bean2SqlException;

    /**
     * @param id 待更新的记录id
     * @param newRecord
     * @return 更新生效的记录条数
     * @throws Bean2SqlException
     */
    int update(long id, @NotNull D newRecord) throws Bean2SqlException;

    /**
     * 按条件查询
     *
     * @param condition
     * @return
     * @throws Bean2SqlException
     */
    List<D> queryByCondition(@NotNull D condition) throws Bean2SqlException;

    /**
     * 多条件查询，condition和sqlCondition是并且的关系
     *
     * @param condition
     * @param sqlCondition
     * @return
     * @throws Bean2SqlException
     */
    List<D> queryByCondition(@NotNull D condition, SqlCondition sqlCondition) throws Bean2SqlException;

    /**
     * 按条件查询
     *
     * @param condition
     * @param c
     * @return
     * @throws Bean2SqlException
     */
    List<D> queryByCondition(@NotNull SqlCondition condition, Class<D> c) throws Bean2SqlException;

    /**
     * 按条件分页查询
     *
     * @param condition
     * @param pageContext
     * @return
     * @throws Bean2SqlException
     */
    PageResult<D> queryByPage(@NotNull D condition, @NotNull PageContext pageContext) throws Bean2SqlException;

    /**
     * 按条件分页查询, condition和sqlCondition是并且的关系
     *
     * @param condition
     * @param sqlCondition
     * @param pageContext
     * @return
     * @throws Bean2SqlException
     */
    PageResult<D> queryByPage(@NotNull D condition, SqlCondition sqlCondition, @NotNull PageContext pageContext) throws Bean2SqlException;

    /**
     * 按条件查询
     *
     * @param sqlCondition
     * @param pageContext
     * @param c
     * @return
     * @throws Bean2SqlException
     */
    PageResult<D> queryByPage(@NotNull SqlCondition sqlCondition, @NotNull PageContext pageContext, Class<D> c) throws Bean2SqlException;
}
