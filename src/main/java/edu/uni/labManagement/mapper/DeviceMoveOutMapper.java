package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.DeviceMoveOut;
import edu.uni.labManagement.bean.DeviceMoveOutExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceMoveOutMapper {
    int countByExample(DeviceMoveOutExample example);

    int deleteByExample(DeviceMoveOutExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DeviceMoveOut record);

    int insertSelective(DeviceMoveOut record);

    List<DeviceMoveOut> selectByExample(DeviceMoveOutExample example);

    DeviceMoveOut selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DeviceMoveOut record, @Param("example") DeviceMoveOutExample example);

    int updateByExample(@Param("record") DeviceMoveOut record, @Param("example") DeviceMoveOutExample example);

    int updateByPrimaryKeySelective(DeviceMoveOut record);

    int updateByPrimaryKey(DeviceMoveOut record);
}