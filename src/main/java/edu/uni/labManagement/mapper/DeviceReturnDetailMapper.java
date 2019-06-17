package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.DeviceReturnDetail;
import edu.uni.labManagement.bean.DeviceReturnDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceReturnDetailMapper {
    int countByExample(DeviceReturnDetailExample example);

    int deleteByExample(DeviceReturnDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DeviceReturnDetail record);

    int insertSelective(DeviceReturnDetail record);

    List<DeviceReturnDetail> selectByExample(DeviceReturnDetailExample example);

    DeviceReturnDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DeviceReturnDetail record, @Param("example") DeviceReturnDetailExample example);

    int updateByExample(@Param("record") DeviceReturnDetail record, @Param("example") DeviceReturnDetailExample example);

    int updateByPrimaryKeySelective(DeviceReturnDetail record);

    int updateByPrimaryKey(DeviceReturnDetail record);
}