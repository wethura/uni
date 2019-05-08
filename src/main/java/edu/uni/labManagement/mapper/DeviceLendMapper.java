package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.DeviceLend;
import edu.uni.labManagement.bean.DeviceLendExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceLendMapper {
    int countByExample(DeviceLendExample example);

    int deleteByExample(DeviceLendExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DeviceLend record);

    int insertSelective(DeviceLend record);

    List<DeviceLend> selectByExample(DeviceLendExample example);

    DeviceLend selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DeviceLend record, @Param("example") DeviceLendExample example);

    int updateByExample(@Param("record") DeviceLend record, @Param("example") DeviceLendExample example);

    int updateByPrimaryKeySelective(DeviceLend record);

    int updateByPrimaryKey(DeviceLend record);
}