package edu.uni.labManagement.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface InStorageService {
    /**
     * 新增设备入库记录
     * @param json 请求参数
     * @return boolean
     */
    boolean insert(Map<String, Object> json);

    /**
     * 删除设备入库记录
     * @param id 主键
     * @return boolean
     */
    boolean delete(Long id);

    /**
     * 修改设备入库记录
     * @param json 请求参数
     * @return boolean
     */
    boolean update(Map<String, Object> json);

    /**
     * 查询单条设备入库记录
     * @param id 主键
     * @return Map<String,Object>
     */
    Map<String,Object> select(Long id);

    /**
     * 分页查询所有设备入库记录
     * @param pageNum 页码
     * @return PageInfo<Map<String,Object>>
     */
    PageInfo<Map<String,Object>> selectPage(int pageNum);

    /**
     * 由设备型号生成设备及其所有子设备
     * @param deviceModelId 设备型号id
     * @param departmentId 部门id
     * @param batch 入库批次
     * @return 生成的设备id
     */
    Long deviceModelToDevice(Long deviceModelId, Long departmentId, Integer batch);

    /**
     * 批量新增设备入库记录
     * @param json 请求参数
     * @return boolean
     */
    boolean insertMore(Map<String, Object> json);
}
