package edu.uni.labManagement.service;

import com.github.pagehelper.PageInfo;
import edu.uni.labManagement.bean.Device;

import java.util.List;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/05/05 11:23
 */
public interface DeviceService {
	PageInfo<Device> listAll(int pageNum);

	List<Device> listAllByLabId(long labId);
}
