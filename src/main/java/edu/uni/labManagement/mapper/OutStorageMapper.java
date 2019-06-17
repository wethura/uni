package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.OutStorage;
import edu.uni.labManagement.bean.OutStorageExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OutStorageMapper {
    int countByExample(OutStorageExample example);

    int deleteByExample(OutStorageExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OutStorage record);

    int insertSelective(OutStorage record);

    List<OutStorage> selectByExample(OutStorageExample example);

    OutStorage selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OutStorage record, @Param("example") OutStorageExample example);

    int updateByExample(@Param("record") OutStorage record, @Param("example") OutStorageExample example);

    int updateByPrimaryKeySelective(OutStorage record);

    int updateByPrimaryKey(OutStorage record);

    Integer selectMaxBatch();
}