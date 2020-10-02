package com.fairyland.xrobot.modular.xrobot.dao.mapper;

import com.fairyland.xrobot.modular.xrobot.domain.DeviceGroup;
import com.fairyland.xrobot.modular.xrobot.domain.DeviceGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeviceGroupMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_devicegroup
     *
     * @mbggenerated
     */
    int countByExample(DeviceGroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_devicegroup
     *
     * @mbggenerated
     */
    int deleteByExample(DeviceGroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_devicegroup
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_devicegroup
     *
     * @mbggenerated
     */
    int insert(DeviceGroup record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_devicegroup
     *
     * @mbggenerated
     */
    int insertSelective(DeviceGroup record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_devicegroup
     *
     * @mbggenerated
     */
    List<DeviceGroup> selectByExample(DeviceGroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_devicegroup
     *
     * @mbggenerated
     */
    DeviceGroup selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_devicegroup
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") DeviceGroup record, @Param("example") DeviceGroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_devicegroup
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") DeviceGroup record, @Param("example") DeviceGroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_devicegroup
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(DeviceGroup record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_devicegroup
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(DeviceGroup record);
}