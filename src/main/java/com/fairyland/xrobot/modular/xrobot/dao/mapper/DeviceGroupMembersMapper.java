package com.fairyland.xrobot.modular.xrobot.dao.mapper;

import com.fairyland.xrobot.modular.xrobot.domain.DeviceGroupMembers;
import com.fairyland.xrobot.modular.xrobot.domain.DeviceGroupMembersExample;
import java.util.List;
import java.util.Map;

import com.fairyland.xrobot.modular.xrobot.domain.req.DeviceGroupMembersInitReq;
import com.fairyland.xrobot.modular.xrobot.domain.req.DeviceGroupMembersListReq;
import com.fairyland.xrobot.modular.xrobot.domain.resp.DeviceGroupMembersInitResp;
import com.fairyland.xrobot.modular.xrobot.domain.resp.DeviceGroupMembersListResp;
import org.apache.ibatis.annotations.Param;

public interface DeviceGroupMembersMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_devicegroupmembers
     *
     * @mbggenerated
     */
    int countByExample(DeviceGroupMembersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_devicegroupmembers
     *
     * @mbggenerated
     */
    int deleteByExample(DeviceGroupMembersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_devicegroupmembers
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_devicegroupmembers
     *
     * @mbggenerated
     */
    int insert(DeviceGroupMembers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_devicegroupmembers
     *
     * @mbggenerated
     */
    int insertSelective(DeviceGroupMembers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_devicegroupmembers
     *
     * @mbggenerated
     */
    List<DeviceGroupMembers> selectByExample(DeviceGroupMembersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_devicegroupmembers
     *
     * @mbggenerated
     */
    DeviceGroupMembers selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_devicegroupmembers
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") DeviceGroupMembers record, @Param("example") DeviceGroupMembersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_devicegroupmembers
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") DeviceGroupMembers record, @Param("example") DeviceGroupMembersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_devicegroupmembers
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(DeviceGroupMembers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_devicegroupmembers
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(DeviceGroupMembers record);

    List<DeviceGroupMembersListResp> deviceGroupMembersList(DeviceGroupMembersListReq paramReq);

    List<DeviceGroupMembersInitResp> saveDeviceGroupMembersInit(DeviceGroupMembersInitReq paramReq);

    void batchInsertData(Map<String, Object> params);

    List<DeviceGroupMembersListResp> deviceGroupMembersAllList(String userName);
}