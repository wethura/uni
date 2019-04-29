package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.LabDevice;
import edu.uni.labManagement.bean.LabDeviceExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LabDeviceMapper {
    int countByExample(LabDeviceExample example);

    int deleteByExample(LabDeviceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LabDevice record);

    int insertSelective(LabDevice record);

    List<LabDevice> selectByExample(LabDeviceExample example);

    LabDevice selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LabDevice record, @Param("example") LabDeviceExample example);

    int updateByExample(@Param("record") LabDevice record, @Param("example") LabDeviceExample example);

    int updateByPrimaryKeySelective(LabDevice record);

    int updateByPrimaryKey(LabDevice record);
}