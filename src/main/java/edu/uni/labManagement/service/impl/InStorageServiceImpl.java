package edu.uni.labManagement.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.uni.example.config.ExampleConfig;
import edu.uni.labManagement.bean.*;
import edu.uni.labManagement.config.LabManagementConfig;
import edu.uni.labManagement.mapper.*;
import edu.uni.labManagement.service.InStorageService;
import edu.uni.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class InStorageServiceImpl implements InStorageService {
    @Resource
    private InStorageMapper inStorageMapper;

    @Resource
    private InStorageDetailMapper inStorageDetailMapper;

    @Resource
    private DeviceModelMapper deviceModelMapper;

    @Resource
    private DeviceMapper deviceMapper;

    @Resource
    private DeviceModelSlavesMapper deviceModelSlavesMapper;

    @Resource
    private DeviceSlaveMapper deviceSlaveMapper;

    @Autowired
    private LabManagementConfig globalConfig;

    private Integer getNextBatch() {
        Integer batch = inStorageMapper.selectMaxBatch();
        return batch==null ? 1 : batch+1;
    }

    /**
     * 新增设备入库记录
     * @param json 请求参数
     * @return boolean
     */
    public boolean insert(Map<String,Object> json) {
        InStorage inStorage = new InStorage();
        inStorage.setBatch(getNextBatch());
        inStorage.setDescription((String) json.get("description"));
        inStorage.setSource((String) json.get("source"));
        inStorage.setDepartmentId(Long.parseLong((String)json.get("departmentId")));
        inStorage.setUniversityId(1L);
        inStorage.setDatetime(new Date());
        inStorage.setByWho(1L);
        inStorage.setDeleted(false);
        if(inStorageMapper.insert(inStorage) <= 0) return false;

        InStorageDetail inStorageDetail = new InStorageDetail();
        inStorageDetail.setStorageId(inStorage.getId());
        inStorageDetail.setUniversityId(inStorage.getUniversityId());
        inStorageDetail.setDatetime(inStorage.getDatetime());
        inStorageDetail.setByWho(inStorage.getByWho());
        inStorageDetail.setDeleted(inStorage.getDeleted());
        for(String id : (List<String>)json.get("arr")) {
            inStorageDetail.setDeviceId(Long.parseLong(id));
            if(inStorageDetailMapper.insert(inStorageDetail) <= 0) return false;
        }
        return true;
    }

    /**
     * 删除设备入库记录
     * @param id 主键
     * @return boolean
     */
    public boolean delete(Long id){
        InStorage inStorage = new InStorage();
        inStorage.setId(id);
        inStorage.setDeleted(true);
        return inStorageMapper.updateByPrimaryKeySelective(inStorage) > 0;
    }

    /**
     * 修改设备入库记录
     * @param json 请求参数
     * @return boolean
     */
    public boolean update(Map<String,Object> json){
        InStorage inStorage = new InStorage();
        inStorage.setId(Long.parseLong((String)json.get("id")));
        inStorage.setDescription((String) json.get("description"));
        inStorage.setSource((String) json.get("source"));
        inStorage.setDepartmentId(Long.parseLong((String)json.get("departmentId")));
        return inStorageMapper.updateByPrimaryKeySelective(inStorage) > 0;
    }

    /**
     * 查询单条设备入库记录
     * @param id 主键
     * @return Map<String,Object>
     */
    public Map<String,Object> select(Long id){
        InStorageExample example = new InStorageExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        List<InStorage> list = inStorageMapper.selectByExample(example);
        if(list.isEmpty()) return null;
        InStorage inStorage = list.get(0);

        Map<String,Object> res = new HashMap<>();
        res.put("id", inStorage.getId());
        res.put("batch", inStorage.getBatch());
        res.put("description", inStorage.getDescription());
        res.put("source", inStorage.getSource());
        res.put("departmentId", inStorage.getDepartmentId());
        res.put("datetime", new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(inStorage.getDatetime()));

        InStorageDetailExample example2 = new InStorageDetailExample();
        example2.or().andStorageIdEqualTo(id).andDeletedEqualTo(false);
        List<InStorageDetail> list2 = inStorageDetailMapper.selectByExample(example2);
        List<Long> arr = new LinkedList<>();
        for(InStorageDetail inStorageDetail : list2) {
            arr.add(inStorageDetail.getDeviceId());
        }
        res.put("arr", arr);
        return res;
    }

    /**
     * 分页查询所有设备入库记录
     * @param pageNum 页码
     * @return PageInfo<Map<String,Object>>
     */
    public PageInfo<Map<String,Object>> selectPage(int pageNum){
        PageHelper.startPage(pageNum, globalConfig.getPageSize()); //开启分页查询，仅第一次查询时生效

        InStorageExample example = new InStorageExample();
        example.or().andDeletedEqualTo(false);
        List<InStorage> res = inStorageMapper.selectByExample(example);

        List<Map<String,Object>> list = new LinkedList<>();
        for(InStorage inStorage : res) {
            list.add(select(inStorage.getId()));
        }

        if(!list.isEmpty()) return new PageInfo<>(list);
        return null;
    }

    /**
     * 由设备型号生成设备及其所有子设备
     * @param deviceModelId 设备型号id
     * @param departmentId 部门id
     * @param batch 入库批次
     * @return 生成的设备id
     */
    public Long deviceModelToDevice(Long deviceModelId, Long departmentId, Integer batch) {
        DeviceModelExample example = new DeviceModelExample();
        example.or().andIdEqualTo(deviceModelId).andDeletedEqualTo(false);
        List<DeviceModel> list = deviceModelMapper.selectByExample(example);
        if(list.isEmpty()) return null;
        DeviceModel deviceModel = list.get(0);

        Device device = new Device();
        device.setDeviceCategoryId(deviceModel.getDeviceCategoryId());
        device.setDepartmentId(departmentId);
        device.setBatch(batch);
        device.setNumber(CommonUtils.generateUUID().substring(0, 8));
        device.setModel(deviceModel.getVersion());
        device.setName(deviceModel.getName());
        device.setSerialNumber(CommonUtils.generateUUID());
        device.setDescription(deviceModel.getDescription());
        device.setProductDate(new Date());
        device.setIsMaster(!deviceModel.getIsSlave());
        device.setStatus(0);
        device.setUniversityId(1L);
        device.setDatetime(new Date());
        device.setByWho(1L);
        device.setDeleted(false);
        if(deviceMapper.insert(device) <= 0) return null;

        DeviceModelSlavesExample example2 = new DeviceModelSlavesExample();
        example2.or().andMaterIdEqualTo(deviceModel.getId()).andDeletedEqualTo(false);
        List<DeviceModelSlaves> list2 = deviceModelSlavesMapper.selectByExample(example2);

        DeviceSlave deviceSlave = new DeviceSlave();
        deviceSlave.setMasterId(device.getId());
        deviceSlave.setUniversityId(device.getUniversityId());
        deviceSlave.setDatetime(device.getDatetime());
        deviceSlave.setByWho(device.getByWho());
        deviceSlave.setDeleted(device.getDeleted());

        for(DeviceModelSlaves slaves : list2) {
            for(int i = 0; i < slaves.getAmount(); i++) {
                Long id = deviceModelToDevice(slaves.getSlaveId(), departmentId, batch);
                if(id == null) break;
                deviceSlave.setSlaveId(id);
                if(deviceSlaveMapper.insert(deviceSlave) <= 0) return null;
            }
        }

        return device.getId();
    }

    /**
     * 批量新增设备入库记录
     * @param json 请求参数
     * @return boolean
     */
    public boolean insertMore(Map<String,Object> json) {
        InStorage inStorage = new InStorage();
        inStorage.setBatch(getNextBatch());
        inStorage.setDescription((String) json.get("DESCRIPTION"));
        inStorage.setSource((String) json.get("SOURCE"));
        inStorage.setDepartmentId(Long.parseLong((String)json.get("DEPARTMENTID")));
        inStorage.setUniversityId(1L);
        inStorage.setDatetime(new Date());
        inStorage.setByWho(1L);
        inStorage.setDeleted(false);
        if(inStorageMapper.insert(inStorage) <= 0) return false;

        InStorageDetail inStorageDetail = new InStorageDetail();
        inStorageDetail.setStorageId(inStorage.getId());
        inStorageDetail.setUniversityId(inStorage.getUniversityId());
        inStorageDetail.setDatetime(inStorage.getDatetime());
        inStorageDetail.setByWho(inStorage.getByWho());
        inStorageDetail.setDeleted(inStorage.getDeleted());

        List<String> ids = (List<String>)json.get("STR");
        List<String> nums = (List<String>)json.get("DIS");
        int len = ids.size();
        for(int t = 0; t < len; t++) {
            Long deviceModelId = Long.parseLong(ids.get(t));
            int num = Integer.parseInt(nums.get(t));
            for(int i = 0; i < num; i++) {
                Long id = deviceModelToDevice(deviceModelId, inStorage.getDepartmentId(), inStorage.getBatch());
                if(id == null) return false;
                inStorageDetail.setDeviceId(id);
                if(inStorageDetailMapper.insert(inStorageDetail) <= 0) return false;
            }
        }
        return true;
    }
}
