package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.EquipmentModelBelongs;
import edu.uni.labManagement.bean.EquipmentModelBelongsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EquipmentModelBelongsMapper {
    int countByExample(EquipmentModelBelongsExample example);

    int deleteByExample(EquipmentModelBelongsExample example);

    int insert(EquipmentModelBelongs record);

    int insertSelective(EquipmentModelBelongs record);

    List<EquipmentModelBelongs> selectByExample(EquipmentModelBelongsExample example);

    int updateByExampleSelective(@Param("record") EquipmentModelBelongs record, @Param("example") EquipmentModelBelongsExample example);

    int updateByExample(@Param("record") EquipmentModelBelongs record, @Param("example") EquipmentModelBelongsExample example);
}