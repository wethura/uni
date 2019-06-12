package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.DeviceLendReturnDetail;
import edu.uni.labManagement.bean.DeviceLendReturnDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeviceLendReturnDetailMapper {
    int countByExample(DeviceLendReturnDetailExample example);

    int deleteByExample(DeviceLendReturnDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DeviceLendReturnDetail record);

    int insertSelective(DeviceLendReturnDetail record);

    List<DeviceLendReturnDetail> selectByExample(DeviceLendReturnDetailExample example);

    DeviceLendReturnDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DeviceLendReturnDetail record, @Param("example") DeviceLendReturnDetailExample example);

    int updateByExample(@Param("record") DeviceLendReturnDetail record, @Param("example") DeviceLendReturnDetailExample example);

    int updateByPrimaryKeySelective(DeviceLendReturnDetail record);

    int updateByPrimaryKey(DeviceLendReturnDetail record);
}