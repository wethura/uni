package edu.uni.labManagement.service.impl;

import edu.uni.labManagement.mapper.DeviceMapper;
import edu.uni.labManagement.service.TTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/04/25 0:18
 */
@Service
public class TTestImpl implements TTest {

	@Autowired
	private DeviceMapper deviceMapper;

	@Override
	public void test() {
		System.out.println(deviceMapper.selectByPrimaryKey((long)1));
	}
}
