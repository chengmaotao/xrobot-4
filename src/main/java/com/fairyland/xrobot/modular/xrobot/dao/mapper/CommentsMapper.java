package com.fairyland.xrobot.modular.xrobot.dao.mapper;

import com.fairyland.xrobot.modular.xrobot.domain.Comments;
import com.fairyland.xrobot.modular.xrobot.domain.CommentsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommentsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_comments
     *
     * @mbggenerated
     */
    int countByExample(CommentsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_comments
     *
     * @mbggenerated
     */
    int deleteByExample(CommentsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_comments
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_comments
     *
     * @mbggenerated
     */
    int insert(Comments record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_comments
     *
     * @mbggenerated
     */
    int insertSelective(Comments record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_comments
     *
     * @mbggenerated
     */
    List<Comments> selectByExampleWithBLOBs(CommentsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_comments
     *
     * @mbggenerated
     */
    List<Comments> selectByExample(CommentsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_comments
     *
     * @mbggenerated
     */
    Comments selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_comments
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") Comments record, @Param("example") CommentsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_comments
     *
     * @mbggenerated
     */
    int updateByExampleWithBLOBs(@Param("record") Comments record, @Param("example") CommentsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_comments
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") Comments record, @Param("example") CommentsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_comments
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Comments record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_comments
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(Comments record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_comments
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Comments record);
}