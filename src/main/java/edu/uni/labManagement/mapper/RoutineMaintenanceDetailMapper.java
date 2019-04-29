package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.RoutineMaintenanceDetail;
import edu.uni.labManagement.bean.RoutineMaintenanceDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoutineMaintenanceDetailMapper {
    int countByExample(RoutineMaintenanceDetailExample example);

    int deleteByExample(RoutineMaintenanceDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RoutineMaintenanceDetail record);

    int insertSelective(RoutineMaintenanceDetail record);

    List<RoutineMaintenanceDetail> selectByExample(RoutineMaintenanceDetailExample example);

    RoutineMaintenanceDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RoutineMaintenanceDetail record, @Param("example") RoutineMaintenanceDetailExample example);

    int updateByExample(@Param("record") RoutineMaintenanceDetail record, @Param("example") RoutineMaintenanceDetailExample example);

    int updateByPrimaryKeySelective(RoutineMaintenanceDetail record);

    int updateByPrimaryKey(RoutineMaintenanceDetail record);
}