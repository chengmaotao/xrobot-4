package com.fairyland.xrobot.modular.xrobot.dao.mapper;

import com.fairyland.xrobot.modular.xrobot.domain.Tasks;
import com.fairyland.xrobot.modular.xrobot.domain.TasksExample;
import com.fairyland.xrobot.modular.xrobot.domain.TasksWithBLOBs;
import java.util.List;

import com.fairyland.xrobot.modular.xrobot.domain.req.SaveTaskInitReq;
import com.fairyland.xrobot.modular.xrobot.domain.req.TaskListReq;
import com.fairyland.xrobot.modular.xrobot.domain.resp.SaveTaskInitResp;
import org.apache.ibatis.annotations.Param;

public interface TasksMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_tasks
     *
     * @mbggenerated
     */
    int countByExample(TasksExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_tasks
     *
     * @mbggenerated
     */
    int deleteByExample(TasksExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_tasks
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_tasks
     *
     * @mbggenerated
     */
    int insert(TasksWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_tasks
     *
     * @mbggenerated
     */
    int insertSelective(TasksWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_tasks
     *
     * @mbggenerated
     */
    List<TasksWithBLOBs> selectByExampleWithBLOBs(TasksExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_tasks
     *
     * @mbggenerated
     */
    List<Tasks> selectByExample(TasksExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_tasks
     *
     * @mbggenerated
     */
    TasksWithBLOBs selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_tasks
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TasksWithBLOBs record, @Param("example") TasksExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_tasks
     *
     * @mbggenerated
     */
    int updateByExampleWithBLOBs(@Param("record") TasksWithBLOBs record, @Param("example") TasksExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_tasks
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") Tasks record, @Param("example") TasksExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_tasks
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TasksWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_tasks
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(TasksWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_tasks
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Tasks record);

    List<TasksWithBLOBs> taskList(TaskListReq paramReq);

    int exeTask(Tasks record);

    List<SaveTaskInitResp> saveTaskInit(SaveTaskInitReq paramReq);

    void setNamesUtf8mb4();
}