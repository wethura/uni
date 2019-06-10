package edu.uni.labManagement.service;

import edu.uni.labManagement.pojo.ExcelDevicePojo;
import edu.uni.labManagement.pojo.ExcelLabPojo;

import java.util.List;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/06/03 20:54
 */
public interface ExcelDataIO {

	public String ExcelLabPojoImport(List<Object> os) throws Exception;

	public String ExcelDevicePojoImport(List<Object> os) throws Exception;

	List<ExcelLabPojo> ExcelLabPojoExport() throws Exception;

	List<ExcelDevicePojo> ExcelDevicePojoExport(Long labId) throws Exception;

	List<String> findCategoryFull(Long id);
}
