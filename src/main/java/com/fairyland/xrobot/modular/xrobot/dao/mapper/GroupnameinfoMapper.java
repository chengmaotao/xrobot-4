package com.fairyland.xrobot.modular.xrobot.dao.mapper;

import com.fairyland.xrobot.modular.xrobot.autoxit.core.req.ClientSubmitTaskResponseReq;
import com.fairyland.xrobot.modular.xrobot.domain.Groupnameinfo;
import com.fairyland.xrobot.modular.xrobot.domain.GroupnameinfoExample;
import java.util.List;
import java.util.Map;

import com.fairyland.xrobot.modular.xrobot.domain.req.GroupnameListReq;
import org.apache.ibatis.annotations.Param;

public interface GroupnameinfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_groupnameinfo
     *
     * @mbggenerated
     */
    int countByExample(GroupnameinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_groupnameinfo
     *
     * @mbggenerated
     */
    int deleteByExample(GroupnameinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_groupnameinfo
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_groupnameinfo
     *
     * @mbggenerated
     */
    int insert(Groupnameinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_groupnameinfo
     *
     * @mbggenerated
     */
    int insertSelective(Groupnameinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_groupnameinfo
     *
     * @mbggenerated
     */
    List<Groupnameinfo> selectByExampleWithBLOBs(GroupnameinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_groupnameinfo
     *
     * @mbggenerated
     */
    List<Groupnameinfo> selectByExample(GroupnameinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_groupnameinfo
     *
     * @mbggenerated
     */
    Groupnameinfo selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_groupnameinfo
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") Groupnameinfo record, @Param("example") GroupnameinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_groupnameinfo
     *
     * @mbggenerated
     */
    int updateByExampleWithBLOBs(@Param("record") Groupnameinfo record, @Param("example") GroupnameinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_groupnameinfo
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") Groupnameinfo record, @Param("example") GroupnameinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_groupnameinfo
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Groupnameinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_groupnameinfo
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(Groupnameinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_groupnameinfo
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Groupnameinfo record);

    Integer findContByParams(Map<String,String> params);

    List<Groupnameinfo> groupnameList(GroupnameListReq paramReq);

    void delGroupnameList(GroupnameListReq paramReq);
}