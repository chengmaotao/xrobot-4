package com.fairyland.xrobot.modular.xrobot.dao.mapper;

import com.fairyland.xrobot.modular.xrobot.domain.Dict;
import com.fairyland.xrobot.modular.xrobot.domain.DictExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DictMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_dict
     *
     * @mbggenerated
     */
    int countByExample(DictExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_dict
     *
     * @mbggenerated
     */
    int deleteByExample(DictExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_dict
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_dict
     *
     * @mbggenerated
     */
    int insert(Dict record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_dict
     *
     * @mbggenerated
     */
    int insertSelective(Dict record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_dict
     *
     * @mbggenerated
     */
    List<Dict> selectByExample(DictExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_dict
     *
     * @mbggenerated
     */
    Dict selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_dict
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") Dict record, @Param("example") DictExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_dict
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") Dict record, @Param("example") DictExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_dict
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Dict record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_dict
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Dict record);
}