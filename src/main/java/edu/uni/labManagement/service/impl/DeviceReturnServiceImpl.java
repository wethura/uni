package edu.uni.labManagement.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.uni.labManagement.bean.DeviceReturn;
import edu.uni.labManagement.bean.DeviceReturnDetail;
import edu.uni.labManagement.bean.DeviceReturnDetailExample;
import edu.uni.labManagement.bean.DeviceReturnExample;
import edu.uni.labManagement.config.LabManagementConfig;
import edu.uni.labManagement.mapper.DeviceReturnDetailMapper;
import edu.uni.labManagement.mapper.DeviceReturnMapper;
import edu.uni.labManagement.service.DeviceReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class DeviceReturnServiceImpl implements DeviceReturnService {
    @Resource
    private DeviceReturnMapper deviceReturnMapper;

    @Resource
    private DeviceReturnDetailMapper deviceReturnDetailMapper;

    @Autowired
    private LabManagementConfig globalConfig;

    /**
     * 新增设备归还记录
     * @param json 请求参数
     * @return boolean
     */
    public boolean insert(Map<String,Object> json) {
        DeviceReturn deviceReturn = new DeviceReturn();
        deviceReturn.setLendId(Long.parseLong((String)json.get("LendId")));
        deviceReturn.setUserId(Long.parseLong((String)json.get("UserId")));
        deviceReturn.setUniversityId(1L);
        deviceReturn.setDatetime(new Date());
        deviceReturn.setByWho(1L);
        deviceReturn.setDeleted(false);
        if(deviceReturnMapper.insert(deviceReturn) <= 0) return false;

        DeviceReturnDetail deviceReturnDetail = new DeviceReturnDetail();
        deviceReturnDetail.setDeviceReturnId(deviceReturn.getId());
        deviceReturnDetail.setUniversityId(deviceReturn.getUniversityId());
        deviceReturnDetail.setDatetime(deviceReturn.getDatetime());
        deviceReturnDetail.setByWho(deviceReturn.getByWho());
        deviceReturnDetail.setDeleted(deviceReturn.getDeleted());
        for(String id : (List<String>)json.get("Arr")) {
            deviceReturnDetail.setDeviceId(Long.parseLong(id));
            if(deviceReturnDetailMapper.insert(deviceReturnDetail) <= 0) return false;
        }
        return true;
    }

    /**
     * 删除设备归还记录
     * @param id 主键
     * @return boolean
     */
    public boolean delete(Long id){
        DeviceReturn deviceReturn = new DeviceReturn();
        deviceReturn.setId(id);
        deviceReturn.setDeleted(true);
        return deviceReturnMapper.updateByPrimaryKeySelective(deviceReturn) > 0;
    }

    /**
     * 修改设备归还记录
     * @param json 请求参数
     * @return boolean
     */
    public boolean update(Map<String,Object> json){
        DeviceReturn deviceReturn = new DeviceReturn();
        deviceReturn.setId(Long.parseLong((String)json.get("Id")));
        deviceReturn.setLendId(Long.parseLong((String)json.get("LendId")));
        deviceReturn.setUserId(Long.parseLong((String)json.get("UserId")));
        return deviceReturnMapper.updateByPrimaryKeySelective(deviceReturn) > 0;
    }

    /**
     * 查询单条设备归还记录
     * @param id 主键
     * @return Map<String,Object>
     */
    public Map<String,Object> select(Long id){
        DeviceReturnExample example = new DeviceReturnExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        List<DeviceReturn> list = deviceReturnMapper.selectByExample(example);
        if(list.isEmpty()) return null;
        DeviceReturn deviceReturn = list.get(0);

        Map<String,Object> res = new HashMap<>();
        res.put("id", deviceReturn.getId());
        res.put("lendId", deviceReturn.getLendId());
        res.put("userId", deviceReturn.getUserId());
        res.put("datetime", new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(deviceReturn.getDatetime()));

        DeviceReturnDetailExample example2 = new DeviceReturnDetailExample();
        example2.or().andDeviceReturnIdEqualTo(id).andDeletedEqualTo(false);
        List<DeviceReturnDetail> list2 = deviceReturnDetailMapper.selectByExample(example2);
        List<Long> arr = new LinkedList<>();
        for(DeviceReturnDetail deviceReturnDetail : list2) {
            arr.add(deviceReturnDetail.getDeviceId());
        }
        res.put("arr", arr);
        return res;
    }

    /**
     * 分页查询所有设备归还记录
     * @param pageNum 页码
     * @return PageInfo<Map<String,Object>>
     */
    public PageInfo<Map<String,Object>> selectPage(int pageNum){
        PageHelper.startPage(pageNum, globalConfig.getPageSize()); //开启分页查询，仅第一次查询时生效

        DeviceReturnExample example = new DeviceReturnExample();
        example.or().andDeletedEqualTo(false);
        List<DeviceReturn> res = deviceReturnMapper.selectByExample(example);

        List<Map<String,Object>> list = new LinkedList<>();
        for(DeviceReturn deviceReturn : res) {
            list.add(select(deviceReturn.getId()));
        }

        if(!list.isEmpty()) return new PageInfo<>(list);
        return null;
    }
}
