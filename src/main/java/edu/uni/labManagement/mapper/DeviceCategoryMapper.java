package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.DeviceCategory;
import edu.uni.labManagement.bean.DeviceCategoryExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceCategoryMapper {
    int countByExample(DeviceCategoryExample example);

    int deleteByExample(DeviceCategoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DeviceCategory record);

    int insertSelective(DeviceCategory record);

    List<DeviceCategory> selectByExample(DeviceCategoryExample example);

    DeviceCategory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DeviceCategory record, @Param("example") DeviceCategoryExample example);

    int updateByExample(@Param("record") DeviceCategory record, @Param("example") DeviceCategoryExample example);

    int updateByPrimaryKeySelective(DeviceCategory record);

    int updateByPrimaryKey(DeviceCategory record);
}