package com.fairyland.xrobot.modular.xrobot.dao.mapper;

import com.fairyland.xrobot.modular.xrobot.domain.CommentJoinGroups;
import com.fairyland.xrobot.modular.xrobot.domain.CommentJoinGroupsExample;
import java.util.List;
import java.util.Map;

import com.fairyland.xrobot.modular.xrobot.domain.req.ExeResultReq;
import org.apache.ibatis.annotations.Param;

public interface CommentJoinGroupsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_commentjoingroups
     *
     * @mbggenerated
     */
    int countByExample(CommentJoinGroupsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_commentjoingroups
     *
     * @mbggenerated
     */
    int deleteByExample(CommentJoinGroupsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_commentjoingroups
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_commentjoingroups
     *
     * @mbggenerated
     */
    int insert(CommentJoinGroups record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_commentjoingroups
     *
     * @mbggenerated
     */
    int insertSelective(CommentJoinGroups record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_commentjoingroups
     *
     * @mbggenerated
     */
    List<CommentJoinGroups> selectByExample(CommentJoinGroupsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_commentjoingroups
     *
     * @mbggenerated
     */
    CommentJoinGroups selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_commentjoingroups
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") CommentJoinGroups record, @Param("example") CommentJoinGroupsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_commentjoingroups
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") CommentJoinGroups record, @Param("example") CommentJoinGroupsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_commentjoingroups
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(CommentJoinGroups record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_commentjoingroups
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(CommentJoinGroups record);

    List<CommentJoinGroups> commentJoinGroupsList(ExeResultReq paramReq);

    void insertCommentJoinGroups(Map<String, Object> dbParams);
}