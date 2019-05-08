package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.DeviceReturn;
import edu.uni.labManagement.bean.DeviceReturnExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceReturnMapper {
    int countByExample(DeviceReturnExample example);

    int deleteByExample(DeviceReturnExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DeviceReturn record);

    int insertSelective(DeviceReturn record);

    List<DeviceReturn> selectByExample(DeviceReturnExample example);

    DeviceReturn selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DeviceReturn record, @Param("example") DeviceReturnExample example);

    int updateByExample(@Param("record") DeviceReturn record, @Param("example") DeviceReturnExample example);

    int updateByPrimaryKeySelective(DeviceReturn record);

    int updateByPrimaryKey(DeviceReturn record);
}