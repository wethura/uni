package edu.uni.labManagement.service;

import edu.uni.labManagement.bean.Device;
import edu.uni.labManagement.pojo.ExcelDevicePojo;
import edu.uni.labManagement.pojo.ExcelLabPojo;

import java.util.Date;
import java.util.List;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/06/03 20:54
 */
public interface ExcelDataIO {

	/**
	 * 实验室导入 类型转换，从人类的数据转为机器的数据
	 * @param os
	 * @return
	 * @throws Exception
	 */
	public String ExcelLabPojoImport(List<Object> os) throws Exception;

	/**
	 * 设备导入 类型转换，从人类的数据转为机器的数据
	 * @param os
	 * @return
	 * @throws Exception
	 */
	public String ExcelDevicePojoImport(List<Object> os) throws Exception;

	/**
	 * 实验室导出 类型转换，从机器数据转为人类的数据
	 * @return
	 * @throws Exception
	 */
	List<ExcelLabPojo> ExcelLabPojoExport() throws Exception;

	/**
	 * 设备导出 类型转换，从机器数据转为人类的数据
	 * @param list
	 * @return
	 * @throws Exception
	 */
	List<ExcelDevicePojo> ExcelDevicePojoExport(List<Device> list) throws Exception;


	/**
	 * 根据实验室、模型ID创建导入该实验室的模板
	 * @param count
	 * @param labId
	 * @param modelId
	 * @param date
	 * @return
	 */
	List<ExcelDevicePojo> ExcelDevicePojoCreateDemoForLab(Long count, Long labId, Long modelId, Date date);



	List<String> findCategoryFull(Long id);
}
