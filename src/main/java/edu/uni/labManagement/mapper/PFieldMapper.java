package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.PField;
import edu.uni.labManagement.bean.PFieldExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PFieldMapper {
    int countByExample(PFieldExample example);

    int deleteByExample(PFieldExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PField record);

    int insertSelective(PField record);

    List<PField> selectByExample(PFieldExample example);

    PField selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PField record, @Param("example") PFieldExample example);

    int updateByExample(@Param("record") PField record, @Param("example") PFieldExample example);

    int updateByPrimaryKeySelective(PField record);

    int updateByPrimaryKey(PField record);
}