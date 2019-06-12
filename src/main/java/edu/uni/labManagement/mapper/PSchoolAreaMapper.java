package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.PSchoolArea;
import edu.uni.labManagement.bean.PSchoolAreaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PSchoolAreaMapper {
    int countByExample(PSchoolAreaExample example);

    int deleteByExample(PSchoolAreaExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PSchoolArea record);

    int insertSelective(PSchoolArea record);

    List<PSchoolArea> selectByExample(PSchoolAreaExample example);

    PSchoolArea selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PSchoolArea record, @Param("example") PSchoolAreaExample example);

    int updateByExample(@Param("record") PSchoolArea record, @Param("example") PSchoolAreaExample example);

    int updateByPrimaryKeySelective(PSchoolArea record);

    int updateByPrimaryKey(PSchoolArea record);
}