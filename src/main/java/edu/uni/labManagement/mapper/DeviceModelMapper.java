package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.DeviceModel;
import edu.uni.labManagement.bean.DeviceModelExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceModelMapper {
    int countByExample(DeviceModelExample example);

    int deleteByExample(DeviceModelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DeviceModel record);

    int insertSelective(DeviceModel record);

    List<DeviceModel> selectByExample(DeviceModelExample example);

    DeviceModel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DeviceModel record, @Param("example") DeviceModelExample example);

    int updateByExample(@Param("record") DeviceModel record, @Param("example") DeviceModelExample example);

    int updateByPrimaryKeySelective(DeviceModel record);

    int updateByPrimaryKey(DeviceModel record);

    List<DeviceModel> selectByPid(Long pid);
}