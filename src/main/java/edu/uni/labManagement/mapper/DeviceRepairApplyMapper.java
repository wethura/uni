package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.DeviceRepairApply;
import edu.uni.labManagement.bean.DeviceRepairApplyExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceRepairApplyMapper {
    int countByExample(DeviceRepairApplyExample example);

    int deleteByExample(DeviceRepairApplyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DeviceRepairApply record);

    int insertSelective(DeviceRepairApply record);

    List<DeviceRepairApply> selectByExample(DeviceRepairApplyExample example);

    DeviceRepairApply selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DeviceRepairApply record, @Param("example") DeviceRepairApplyExample example);

    int updateByExample(@Param("record") DeviceRepairApply record, @Param("example") DeviceRepairApplyExample example);

    int updateByPrimaryKeySelective(DeviceRepairApply record);

    int updateByPrimaryKey(DeviceRepairApply record);
}