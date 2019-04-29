package edu.uni.labManagement.service.impl;

import edu.uni.labManagement.service.TTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/04/24 23:33
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("edu.uni.*.mapper")
public class testImplTest {

	@Autowired
	private TTest ttest;

	@Test
	public void test1() {
		ttest.test();
	}
}