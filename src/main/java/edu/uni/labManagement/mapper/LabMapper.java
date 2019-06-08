package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.Lab;
import edu.uni.labManagement.bean.LabExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

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

	String selectAddressByFieldID(long id);

    List<Map<String,Object>> selectByTwo();

}