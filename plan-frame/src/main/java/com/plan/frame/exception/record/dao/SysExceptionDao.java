package com.plan.frame.exception.record.dao;

import java.util.List;

import com.plan.frame.exception.record.entity.SysException;
import com.plan.frame.mybatis.MyBatisTwoDao;
import org.apache.ibatis.annotations.Param;

@MyBatisTwoDao
public interface SysExceptionDao {
    /**
     *  根据主键删除数据库的记录,SYS_EXCEPTION
     *
     * @param exceptionId
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(@Param("exceptionId") String exceptionId);

    /**
     *  根据实体类属性值动态生成删除条件,SYS_EXCEPTION
     *
     * @param record
     *
     * @mbggenerated
     */
    int deleteByEntitySelective(SysException record);

    /**
     *  新写入数据库记录,SYS_EXCEPTION
     *
     * @param record
     *
     * @mbggenerated
     */
    int insert(SysException record);

    /**
     *  动态字段,写入数据库记录,SYS_EXCEPTION
     *
     * @param record
     *
     * @mbggenerated
     */
    int insertSelective(SysException record);

    /**
     *  根据指定主键获取一条数据库记录,SYS_EXCEPTION
     *
     * @param exceptionId
     *
     * @mbggenerated
     */
    SysException selectByPrimaryKey(@Param("exceptionId") String exceptionId);

    /**
     *  根据实体类属性值动态生成查询条件,SYS_EXCEPTION
     *
     * @param record
     *
     * @mbggenerated
     */
    List<SysException> selectByEntitySelective(SysException record);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,SYS_EXCEPTION
     *
     * @param record
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SysException record);

    /**
     *  根据实体类属性值动态生成修改条件,SYS_EXCEPTION
     *
     * @param record
     * @param condition
     *
     * @mbggenerated
     */
    int updateByEntitySelective(@Param("record") SysException record, @Param("condition") SysException condition);

    /**
     * ,SYS_EXCEPTION
     *
     * @param record
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(SysException record);
}