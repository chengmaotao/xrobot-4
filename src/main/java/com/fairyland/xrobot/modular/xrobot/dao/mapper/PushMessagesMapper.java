package com.fairyland.xrobot.modular.xrobot.dao.mapper;

import com.fairyland.xrobot.modular.xrobot.domain.PushMessages;
import com.fairyland.xrobot.modular.xrobot.domain.PushMessagesExample;
import java.util.List;

import com.fairyland.xrobot.modular.xrobot.domain.req.ExeResultReq;
import org.apache.ibatis.annotations.Param;

public interface PushMessagesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_pushmessages
     *
     * @mbggenerated
     */
    int countByExample(PushMessagesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_pushmessages
     *
     * @mbggenerated
     */
    int deleteByExample(PushMessagesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_pushmessages
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_pushmessages
     *
     * @mbggenerated
     */
    int insert(PushMessages record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_pushmessages
     *
     * @mbggenerated
     */
    int insertSelective(PushMessages record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_pushmessages
     *
     * @mbggenerated
     */
    List<PushMessages> selectByExample(PushMessagesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_pushmessages
     *
     * @mbggenerated
     */
    PushMessages selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_pushmessages
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") PushMessages record, @Param("example") PushMessagesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_pushmessages
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") PushMessages record, @Param("example") PushMessagesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_pushmessages
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(PushMessages record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_pushmessages
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(PushMessages record);

    List<PushMessages> pushMessagesList(ExeResultReq paramReq);
}