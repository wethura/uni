package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.RoutineMaintenance;
import edu.uni.labManagement.bean.RoutineMaintenanceExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoutineMaintenanceMapper {
    int countByExample(RoutineMaintenanceExample example);

    int deleteByExample(RoutineMaintenanceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RoutineMaintenance record);

    int insertSelective(RoutineMaintenance record);

    List<RoutineMaintenance> selectByExample(RoutineMaintenanceExample example);

    RoutineMaintenance selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RoutineMaintenance record, @Param("example") RoutineMaintenanceExample example);

    int updateByExample(@Param("record") RoutineMaintenance record, @Param("example") RoutineMaintenanceExample example);

    int updateByPrimaryKeySelective(RoutineMaintenance record);

    int updateByPrimaryKey(RoutineMaintenance record);
}