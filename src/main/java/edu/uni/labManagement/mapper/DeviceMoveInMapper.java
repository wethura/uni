package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.DeviceMoveIn;
import edu.uni.labManagement.bean.DeviceMoveInExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceMoveInMapper {
    int countByExample(DeviceMoveInExample example);

    int deleteByExample(DeviceMoveInExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DeviceMoveIn record);

    int insertSelective(DeviceMoveIn record);

    List<DeviceMoveIn> selectByExample(DeviceMoveInExample example);

    DeviceMoveIn selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DeviceMoveIn record, @Param("example") DeviceMoveInExample example);

    int updateByExample(@Param("record") DeviceMoveIn record, @Param("example") DeviceMoveInExample example);

    int updateByPrimaryKeySelective(DeviceMoveIn record);

    int updateByPrimaryKey(DeviceMoveIn record);
}