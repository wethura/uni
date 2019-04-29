package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.InStorage;
import edu.uni.labManagement.bean.InStorageExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InStorageMapper {
    int countByExample(InStorageExample example);

    int deleteByExample(InStorageExample example);

    int deleteByPrimaryKey(Long id);

    int insert(InStorage record);

    int insertSelective(InStorage record);

    List<InStorage> selectByExample(InStorageExample example);

    InStorage selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") InStorage record, @Param("example") InStorageExample example);

    int updateByExample(@Param("record") InStorage record, @Param("example") InStorageExample example);

    int updateByPrimaryKeySelective(InStorage record);

    int updateByPrimaryKey(InStorage record);
}