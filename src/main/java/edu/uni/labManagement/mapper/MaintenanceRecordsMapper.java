package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.MaintenanceRecords;
import edu.uni.labManagement.bean.MaintenanceRecordsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MaintenanceRecordsMapper {
    int countByExample(MaintenanceRecordsExample example);

    int deleteByExample(MaintenanceRecordsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MaintenanceRecords record);

    int insertSelective(MaintenanceRecords record);

    List<MaintenanceRecords> selectByExample(MaintenanceRecordsExample example);

    MaintenanceRecords selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MaintenanceRecords record, @Param("example") MaintenanceRecordsExample example);

    int updateByExample(@Param("record") MaintenanceRecords record, @Param("example") MaintenanceRecordsExample example);

    int updateByPrimaryKeySelective(MaintenanceRecords record);

    int updateByPrimaryKey(MaintenanceRecords record);

    List<MaintenanceRecords> selectByLabId(Long labId);

    String selectUserById(long id);
}