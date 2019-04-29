package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.DeviceSlave;
import edu.uni.labManagement.bean.DeviceSlaveExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceSlaveMapper {
    int countByExample(DeviceSlaveExample example);

    int deleteByExample(DeviceSlaveExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DeviceSlave record);

    int insertSelective(DeviceSlave record);

    List<DeviceSlave> selectByExample(DeviceSlaveExample example);

    DeviceSlave selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DeviceSlave record, @Param("example") DeviceSlaveExample example);

    int updateByExample(@Param("record") DeviceSlave record, @Param("example") DeviceSlaveExample example);

    int updateByPrimaryKeySelective(DeviceSlave record);

    int updateByPrimaryKey(DeviceSlave record);
}