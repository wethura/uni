package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.LabAdmin;
import edu.uni.labManagement.bean.LabAdminExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LabAdminMapper {
    int countByExample(LabAdminExample example);

    int deleteByExample(LabAdminExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LabAdmin record);

    int insertSelective(LabAdmin record);

    List<LabAdmin> selectByExample(LabAdminExample example);

    LabAdmin selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LabAdmin record, @Param("example") LabAdminExample example);

    int updateByExample(@Param("record") LabAdmin record, @Param("example") LabAdminExample example);

    int updateByPrimaryKeySelective(LabAdmin record);

    int updateByPrimaryKey(LabAdmin record);

    List<String> selectByLabId(Long id);


}