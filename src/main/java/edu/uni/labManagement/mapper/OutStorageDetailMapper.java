package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.OutStorageDetail;
import edu.uni.labManagement.bean.OutStorageDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OutStorageDetailMapper {
    int countByExample(OutStorageDetailExample example);

    int deleteByExample(OutStorageDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OutStorageDetail record);

    int insertSelective(OutStorageDetail record);

    List<OutStorageDetail> selectByExample(OutStorageDetailExample example);

    OutStorageDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OutStorageDetail record, @Param("example") OutStorageDetailExample example);

    int updateByExample(@Param("record") OutStorageDetail record, @Param("example") OutStorageDetailExample example);

    int updateByPrimaryKeySelective(OutStorageDetail record);

    int updateByPrimaryKey(OutStorageDetail record);
}