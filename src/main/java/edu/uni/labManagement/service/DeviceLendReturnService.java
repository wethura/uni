package edu.uni.labManagement.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface DeviceLendReturnService {
    /**
     * 借出设备
     * @param json 请求参数
     * @return boolean
     */
    boolean insert(Map<String, Object> json) throws Exception;

    /**
     * 审批借出申请
     * @param json 请求参数
     * @return boolean
     */
    boolean updateApproval(Map<String, Object> json);

    /**
     * 归还设备
     * @param json 请求参数
     * @return boolean
     */
    boolean updateReturned(Map<String, Object> json);

    /**
     * 查询单条设备借出归还记录
     * @param id 主键
     * @return Map<String,Object>
     */
    Map<String,Object> select(Long id);

    /**
     * 分页查询可审批的借出申请
     * @param pageNum 页码
     * @param pageSize 每页显示条数
     * @return PageInfo<Map<String,Object>>
     */
    PageInfo<Map<String,Object>> selectPageApproval(int pageNum, int pageSize);

    /**
     * 分页查询可归还的设备
     * @param pageNum 页码
     * @param pageSize 每页显示条数
     * @return PageInfo<Map<String,Object>>
     */
    PageInfo<Map<String,Object>> selectPageReturned(int pageNum, int pageSize);

    /**
     * 分页高级筛选可归还的设备
     * @param json 请求参数
     * @return PageInfo<Map<String,Object>>
     */
    PageInfo<Map<String,Object>> filterReturned(Map<String, Object> json);
}
