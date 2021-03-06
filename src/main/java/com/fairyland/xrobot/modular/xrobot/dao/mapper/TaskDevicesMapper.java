package com.fairyland.xrobot.modular.xrobot.dao.mapper;

import com.fairyland.xrobot.modular.xrobot.autoxit.core.req.ServerTaskNotifyCommandReq;
import com.fairyland.xrobot.modular.xrobot.domain.TaskDevices;
import com.fairyland.xrobot.modular.xrobot.domain.TaskDevicesExample;
import java.util.List;
import java.util.Map;

import com.fairyland.xrobot.modular.xrobot.domain.req.TaskDevicesListReq;
import com.fairyland.xrobot.modular.xrobot.domain.req.TaskExeResultReq;
import com.fairyland.xrobot.modular.xrobot.domain.resp.TaskDevicesResp;
import com.fairyland.xrobot.modular.xrobot.domain.resp.TaskExeResultResp;
import org.apache.ibatis.annotations.Param;

public interface TaskDevicesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_taskdevices
     *
     * @mbggenerated
     */
    int countByExample(TaskDevicesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_taskdevices
     *
     * @mbggenerated
     */
    int deleteByExample(TaskDevicesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_taskdevices
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_taskdevices
     *
     * @mbggenerated
     */
    int insert(TaskDevices record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_taskdevices
     *
     * @mbggenerated
     */
    int insertSelective(TaskDevices record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_taskdevices
     *
     * @mbggenerated
     */
    List<TaskDevices> selectByExample(TaskDevicesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_taskdevices
     *
     * @mbggenerated
     */
    TaskDevices selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_taskdevices
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TaskDevices record, @Param("example") TaskDevicesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_taskdevices
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TaskDevices record, @Param("example") TaskDevicesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_taskdevices
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TaskDevices record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_taskdevices
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TaskDevices record);

    void insertTasksDevices(Map<String, Object> dbParams);

    List<ServerTaskNotifyCommandReq> getClientGetTaskByDeviceId(String deviceid);

    List<TaskDevicesResp> selectTaskDevicesList(TaskDevicesListReq paramReq);

    List<TaskExeResultResp> taskExeResult(TaskExeResultReq paramReq);

    void exeTaskDevices(@Param("record") TaskDevices record, @Param("example") TaskDevicesExample example);
}