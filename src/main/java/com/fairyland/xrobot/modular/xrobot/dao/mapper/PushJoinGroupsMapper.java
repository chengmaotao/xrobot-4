package com.fairyland.xrobot.modular.xrobot.dao.mapper;

import com.fairyland.xrobot.modular.xrobot.domain.PushJoinGroups;
import com.fairyland.xrobot.modular.xrobot.domain.PushJoinGroupsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PushJoinGroupsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_pushjoingroups
     *
     * @mbggenerated
     */
    int countByExample(PushJoinGroupsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_pushjoingroups
     *
     * @mbggenerated
     */
    int deleteByExample(PushJoinGroupsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_pushjoingroups
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_pushjoingroups
     *
     * @mbggenerated
     */
    int insert(PushJoinGroups record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_pushjoingroups
     *
     * @mbggenerated
     */
    int insertSelective(PushJoinGroups record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_pushjoingroups
     *
     * @mbggenerated
     */
    List<PushJoinGroups> selectByExample(PushJoinGroupsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_pushjoingroups
     *
     * @mbggenerated
     */
    PushJoinGroups selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_pushjoingroups
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") PushJoinGroups record, @Param("example") PushJoinGroupsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_pushjoingroups
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") PushJoinGroups record, @Param("example") PushJoinGroupsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_pushjoingroups
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(PushJoinGroups record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_pushjoingroups
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(PushJoinGroups record);
}