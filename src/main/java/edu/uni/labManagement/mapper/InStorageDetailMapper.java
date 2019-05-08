package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.InStorageDetail;
import edu.uni.labManagement.bean.InStorageDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InStorageDetailMapper {
    int countByExample(InStorageDetailExample example);

    int deleteByExample(InStorageDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(InStorageDetail record);

    int insertSelective(InStorageDetail record);

    List<InStorageDetail> selectByExample(InStorageDetailExample example);

    InStorageDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") InStorageDetail record, @Param("example") InStorageDetailExample example);

    int updateByExample(@Param("record") InStorageDetail record, @Param("example") InStorageDetailExample example);

    int updateByPrimaryKeySelective(InStorageDetail record);

    int updateByPrimaryKey(InStorageDetail record);
}