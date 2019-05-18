package edu.uni.labManagement.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface OutStorageService {
    /**
     * 新增设备出库记录
     * @param json 请求参数
     * @return boolean
     */
    boolean insert(Map<String, Object> json);

    /**
     * 删除设备出库记录
     * @param id 主键
     * @return boolean
     */
    boolean delete(Long id);

    /**
     * 修改设备出库记录
     * @param json 请求参数
     * @return boolean
     */
    boolean update(Map<String, Object> json);

    /**
     * 查询单条设备出库记录
     * @param id 主键
     * @return Map<String,Object>
     */
    Map<String,Object> select(Long id);

    /**
     * 分页查询所有设备出库记录
     * @param pageNum 页码
     * @return PageInfo<Map<String,Object>>
     */
    PageInfo<Map<String,Object>> selectPage(int pageNum);
}
