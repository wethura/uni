package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.DeviceLendDetail;
import edu.uni.labManagement.bean.DeviceLendDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceLendDetailMapper {
    int countByExample(DeviceLendDetailExample example);

    int deleteByExample(DeviceLendDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DeviceLendDetail record);

    int insertSelective(DeviceLendDetail record);

    List<DeviceLendDetail> selectByExample(DeviceLendDetailExample example);

    DeviceLendDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DeviceLendDetail record, @Param("example") DeviceLendDetailExample example);

    int updateByExample(@Param("record") DeviceLendDetail record, @Param("example") DeviceLendDetailExample example);

    int updateByPrimaryKeySelective(DeviceLendDetail record);

    int updateByPrimaryKey(DeviceLendDetail record);
}