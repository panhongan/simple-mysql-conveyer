package com.github.panhongan.mysql.conveyer.bean2sql.table;

import com.github.panhongan.mysql.conveyer.bean2sql.orderby.OrderBy;
import com.github.panhongan.mysql.conveyer.commons.MysqlConveyerException;
import com.github.panhongan.mysql.conveyer.commons.PageResult;
import com.github.panhongan.mysql.conveyer.bean2sql.condition.SqlCondition;
import net.sf.oval.constraint.NotNull;

import java.util.List;
import java.util.Optional;

/**
 * @param <D> Data Object mapping to db
 *
 * @author panhongan
 * @since 2019.7.14
 * @version 1.0
 */

public interface TableAccess<D> {

    String getTable();

    D emptyDO();

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

    Optional<D> queryById(long id) throws MysqlConveyerException;

    /**
     * 按条件查询
     *
     * @param condition 查询条件对象
     * @param orderBy OrderBy
     * @return 返回符合条件的所有数据对象
     * @throws MysqlConveyerException MysqlConveyerException
     */
    List<D> queryByCondition(@NotNull D condition, OrderBy orderBy) throws MysqlConveyerException;

    /**
     * 多条件查询，condition和sqlCondition是并且的关系
     *
     * @param condition 查询条件对象
     * @param sqlCondition SqlCondition
     * @param orderBy OrderBy
     * @return List 返回符合条件的所有数据对象
     * @throws MysqlConveyerException MysqlConveyerException
     */
    List<D> queryByCondition(@NotNull D condition, SqlCondition sqlCondition, OrderBy orderBy) throws MysqlConveyerException;

    /**
     * 按条件查询
     *
     * @param condition SqlCondition
     * @param orderBy OrderBy
     * @param c DO对象的类类型
     * @return List 返回符合条件的所有数据对象
     * @throws MysqlConveyerException MysqlConveyerException
     */
    List<D> queryByCondition(@NotNull SqlCondition condition, OrderBy orderBy, Class<D> c) throws MysqlConveyerException;

    /**
     * 按条件分页查询
     *
     * @param condition Data Object
     * @param orderBy OrderBy
     * @param pageContext PageContext
     * @return PageResult 分页结果
     * @throws MysqlConveyerException MysqlConveyerException
     */
    PageResult<D> queryByPage(@NotNull D condition, OrderBy orderBy, @NotNull PageContext pageContext) throws MysqlConveyerException;

    /**
     * 按条件分页查询, condition和sqlCondition是并且的关系
     *
     * @param condition Data Object
     * @param sqlCondition SqlCondition
     * @param orderBy OrderBy
     * @param pageContext PageContext
     * @return PageResult 分页结果
     * @throws MysqlConveyerException MysqlConveyerException
     */
    PageResult<D> queryByPage(@NotNull D condition, SqlCondition sqlCondition, OrderBy orderBy, @NotNull PageContext pageContext) throws MysqlConveyerException;

    /**
     * 分页查询
     *
     * @param sqlCondition SqlCondition
     * @param orderBy OrderBy
     * @param pageContext PageContext
     * @param c DO对象类的类型
     * @return PageResult 分页结果
     * @throws MysqlConveyerException MysqlConveyerException
     */
    PageResult<D> queryByPage(@NotNull SqlCondition sqlCondition, OrderBy orderBy, @NotNull PageContext pageContext, Class<D> c) throws MysqlConveyerException;
}
