package com.fairyland.xrobot.modular.xrobot.dao.mapper;

import com.fairyland.xrobot.modular.xrobot.domain.SummaryJoinGroups;
import com.fairyland.xrobot.modular.xrobot.domain.SummaryJoinGroupsExample;
import java.util.List;

import com.fairyland.xrobot.modular.xrobot.domain.req.ExeResultReq;
import org.apache.ibatis.annotations.Param;

public interface SummaryJoinGroupsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_summaryjoingroups
     *
     * @mbggenerated
     */
    int countByExample(SummaryJoinGroupsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_summaryjoingroups
     *
     * @mbggenerated
     */
    int deleteByExample(SummaryJoinGroupsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_summaryjoingroups
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_summaryjoingroups
     *
     * @mbggenerated
     */
    int insert(SummaryJoinGroups record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_summaryjoingroups
     *
     * @mbggenerated
     */
    int insertSelective(SummaryJoinGroups record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_summaryjoingroups
     *
     * @mbggenerated
     */
    List<SummaryJoinGroups> selectByExample(SummaryJoinGroupsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_summaryjoingroups
     *
     * @mbggenerated
     */
    SummaryJoinGroups selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_summaryjoingroups
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") SummaryJoinGroups record, @Param("example") SummaryJoinGroupsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_summaryjoingroups
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") SummaryJoinGroups record, @Param("example") SummaryJoinGroupsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_summaryjoingroups
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SummaryJoinGroups record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_summaryjoingroups
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SummaryJoinGroups record);

    List<SummaryJoinGroups> summaryJoinGroupsList(ExeResultReq paramReq);
}