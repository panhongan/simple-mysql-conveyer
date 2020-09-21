package com.github.panhongan.bean2sql.table;

import com.github.panhongan.commons.MysqlConveyerException;
import com.github.panhongan.commons.PageResult;
import com.github.panhongan.bean2sql.condition.SqlCondition;
import net.sf.oval.constraint.NotNull;

import java.util.List;

/**
 * @param <D> Data Object mapping to db
 *
 * @author panhongan
 * @since 2019.7.14
 * @version 1.0
 */

public interface TableAccess<D> {

    /**
     * 查询满足条件记录的最大ID
     *
     * @param sqlCondition SqlCondition
     * @return long 最大id的记录
     */
    long getMaxRowId(SqlCondition sqlCondition);

    /**
     *
     * @param record 待插入对象
     * @return 插入数据后返回的记录id
     * @throws MysqlConveyerException MysqlConveyerException
     */
    long insert(@NotNull D record) throws MysqlConveyerException;

    /**
     * @param id 待删除记录id
     * @return 删除生效的条数
     * @throws MysqlConveyerException MysqlConveyerException
     */
    int deleteById(long id) throws MysqlConveyerException;

    /**
     * @param id 待更新的记录id
     * @param newRecord 待修改的新记录
     * @return 更新生效的记录条数
     * @throws MysqlConveyerException MysqlConveyerException
     */
    int update(long id, @NotNull D newRecord) throws MysqlConveyerException;

    /**
     * 按条件查询
     *
     * @param condition 查询条件对象
     * @return 返回符合条件的所有数据对象
     * @throws MysqlConveyerException MysqlConveyerException
     */
    List<D> queryByCondition(@NotNull D condition) throws MysqlConveyerException;

    /**
     * 多条件查询，condition和sqlCondition是并且的关系
     *
     * @param condition 查询条件对象
     * @param sqlCondition SqlCondition
     * @return List 返回符合条件的所有数据对象
     * @throws MysqlConveyerException MysqlConveyerException
     */
    List<D> queryByCondition(@NotNull D condition, SqlCondition sqlCondition) throws MysqlConveyerException;

    /**
     * 按条件查询
     *
     * @param condition SqlCondition
     * @param c DO对象的类类型
     * @return List 返回符合条件的所有数据对象
     * @throws MysqlConveyerException MysqlConveyerException
     */
    List<D> queryByCondition(@NotNull SqlCondition condition, Class<D> c) throws MysqlConveyerException;

    /**
     * 按条件分页查询
     *
     * @param condition Data Object
     * @param pageContext PageContext
     * @return PageResult 分页结果
     * @throws MysqlConveyerException MysqlConveyerException
     */
    PageResult<D> queryByPage(@NotNull D condition, @NotNull PageContext pageContext) throws MysqlConveyerException;

    /**
     * 按条件分页查询, condition和sqlCondition是并且的关系
     *
     * @param condition Data Object
     * @param sqlCondition SqlCondition
     * @param pageContext PageContext
     * @return PageResult 分页结果
     * @throws MysqlConveyerException MysqlConveyerException
     */
    PageResult<D> queryByPage(@NotNull D condition, SqlCondition sqlCondition, @NotNull PageContext pageContext) throws MysqlConveyerException;

    /**
     * 分页查询
     *
     * @param sqlCondition SqlCondition
     * @param pageContext PageContext
     * @param c DO对象类的类型
     * @return PageResult 分页结果
     * @throws MysqlConveyerException MysqlConveyerException
     */
    PageResult<D> queryByPage(@NotNull SqlCondition sqlCondition, @NotNull PageContext pageContext, Class<D> c) throws MysqlConveyerException;
}
