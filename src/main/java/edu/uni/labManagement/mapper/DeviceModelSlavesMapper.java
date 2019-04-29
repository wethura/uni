package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.DeviceModelSlaves;
import edu.uni.labManagement.bean.DeviceModelSlavesExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceModelSlavesMapper {
    int countByExample(DeviceModelSlavesExample example);

    int deleteByExample(DeviceModelSlavesExample example);

    int insert(DeviceModelSlaves record);

    int insertSelective(DeviceModelSlaves record);

    List<DeviceModelSlaves> selectByExample(DeviceModelSlavesExample example);

    int updateByExampleSelective(@Param("record") DeviceModelSlaves record, @Param("example") DeviceModelSlavesExample example);

    int updateByExample(@Param("record") DeviceModelSlaves record, @Param("example") DeviceModelSlavesExample example);
}