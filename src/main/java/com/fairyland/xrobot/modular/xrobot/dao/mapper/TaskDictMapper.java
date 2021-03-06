package com.fairyland.xrobot.modular.xrobot.dao.mapper;

import com.fairyland.xrobot.modular.xrobot.domain.TaskDict;
import com.fairyland.xrobot.modular.xrobot.domain.TaskDictExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TaskDictMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_taskdict
     *
     * @mbggenerated
     */
    int countByExample(TaskDictExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_taskdict
     *
     * @mbggenerated
     */
    int deleteByExample(TaskDictExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_taskdict
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_taskdict
     *
     * @mbggenerated
     */
    int insert(TaskDict record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_taskdict
     *
     * @mbggenerated
     */
    int insertSelective(TaskDict record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_taskdict
     *
     * @mbggenerated
     */
    List<TaskDict> selectByExample(TaskDictExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_taskdict
     *
     * @mbggenerated
     */
    TaskDict selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_taskdict
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TaskDict record, @Param("example") TaskDictExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_taskdict
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TaskDict record, @Param("example") TaskDictExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_taskdict
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TaskDict record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_taskdict
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TaskDict record);
}