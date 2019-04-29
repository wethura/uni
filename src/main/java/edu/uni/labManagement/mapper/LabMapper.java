package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.Lab;
import edu.uni.labManagement.bean.LabExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LabMapper {
    int countByExample(LabExample example);

    int deleteByExample(LabExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Lab record);

    int insertSelective(Lab record);

    List<Lab> selectByExample(LabExample example);

    Lab selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Lab record, @Param("example") LabExample example);

    int updateByExample(@Param("record") Lab record, @Param("example") LabExample example);

    int updateByPrimaryKeySelective(Lab record);

    int updateByPrimaryKey(Lab record);
}