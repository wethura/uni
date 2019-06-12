package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.PSchool;
import edu.uni.labManagement.bean.PSchoolExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PSchoolMapper {
    int countByExample(PSchoolExample example);

    int deleteByExample(PSchoolExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PSchool record);

    int insertSelective(PSchool record);

    List<PSchool> selectByExample(PSchoolExample example);

    PSchool selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PSchool record, @Param("example") PSchoolExample example);

    int updateByExample(@Param("record") PSchool record, @Param("example") PSchoolExample example);

    int updateByPrimaryKeySelective(PSchool record);

    int updateByPrimaryKey(PSchool record);
}