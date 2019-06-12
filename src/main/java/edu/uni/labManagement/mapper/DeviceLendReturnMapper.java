package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.DeviceLendReturn;
import edu.uni.labManagement.bean.DeviceLendReturnExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeviceLendReturnMapper {
    int countByExample(DeviceLendReturnExample example);

    int deleteByExample(DeviceLendReturnExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DeviceLendReturn record);

    int insertSelective(DeviceLendReturn record);

    List<DeviceLendReturn> selectByExample(DeviceLendReturnExample example);

    DeviceLendReturn selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DeviceLendReturn record, @Param("example") DeviceLendReturnExample example);

    int updateByExample(@Param("record") DeviceLendReturn record, @Param("example") DeviceLendReturnExample example);

    int updateByPrimaryKeySelective(DeviceLendReturn record);

    int updateByPrimaryKey(DeviceLendReturn record);
}