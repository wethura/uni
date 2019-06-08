package edu.uni.labManagement.controller;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.labManagement.pojo.ExcelDevicePojo;
import edu.uni.labManagement.pojo.ExcelLabPojo;
import edu.uni.labManagement.service.ExcelDataIO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/06/02 17:56
 */
//@Api(value = "用于搞定Excel的上传以及数据的导入导出", tags = {"Excel 导入导出"})
@Api(description = "excel上传")
@Controller
@RequestMapping("json/labManagement")
public class ExcelUploadController {

	/**
	 * 文件导入思路
	 * inputStream = multipartFile.getInputStream
	 * List<Object> list = EasyExcelFactory.read(inpputStream, new Sheet(1, 0, 接收类.class))
	 * 对象OB - > 实际使用的类，一个一个转
	 *
	 * 将得到的数据进行转化-->成功转化：存入数据库 失败：返回错误描述。提示Excel变化
	 */
	@Autowired
	private ExcelDataIO excelDataIO;


	@ApiOperation(value = "实验室导入", notes = "已测试")
	@PostMapping("/excel/lab")
	@ResponseBody
	public void receive1(@ApiParam(value = "导入的文件", required = true) MultipartFile multipartFile,
	                    HttpServletResponse response)throws Exception{
		response.setContentType("application/json;charset=utf-8");
		InputStream inputStream = null;
		String message = null;
		try {
			inputStream = multipartFile.getInputStream();
			List<Object> list = EasyExcelFactory.read(inputStream, new Sheet(1, 0, ExcelLabPojo.class));
			message = excelDataIO.ExcelLabPojoImport(list);
		} catch (Exception e) {
			response.getWriter().write(Result.build(ResultType.Failed).appendData("res", e.getMessage()).convertIntoJSON());
			return;
		} finally {
			inputStream.close();
		}
		response.getWriter().write(Result.build(ResultType.Success).appendData("res", message).convertIntoJSON());
	}

	@ApiOperation(value = "设备导入")
	@PostMapping("/excel/device")
	@ResponseBody
	public void receive2(@ApiParam(value = "导入的文件", required = true) MultipartFile multipartFile,
	                    HttpServletResponse response) throws Exception{
		response.setContentType("application/json;charset=utf-8");
		InputStream inputStream = null;
		String message = null;
		try {
			inputStream = multipartFile.getInputStream();
			List<Object> list = EasyExcelFactory.read(inputStream, new Sheet(1, 0, ExcelDevicePojo.class));
			message = excelDataIO.ExcelDevicePojoImport(list);
		} catch (Exception e) {
			response.getWriter().write(Result.build(ResultType.Failed).appendData("res", e.getMessage()).convertIntoJSON());
			return;
		} finally {
			inputStream.close();
		}
		response.getWriter().write(Result.build(ResultType.Success).appendData("res", message).convertIntoJSON());
	}

	@ApiOperation(value = "实验室导出")
	@GetMapping("/excel/lab")
	@ResponseBody
	public void get1(HttpServletResponse response) throws Exception{
		response.setContentType("application/octet-stream");
		FileInputStream fis = null;
		try {
			File file = new File("Export.xls");

//			写进去Excel
			OutputStream out = new FileOutputStream(file);
			List<ExcelLabPojo> list = excelDataIO.ExcelLabPojoExport();
			System.out.println("------------------" + list + "--------------------");

			ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLS, true);
			Sheet sheet = new Sheet(1, 0, ExcelLabPojo.class, "实验室", null);
			sheet.setAutoWidth(true);

			writer.write(list, sheet);
			writer.finish();
//			out.flush();
			out.close();


			fis = new FileInputStream(file);
			response.setHeader("Content-Disposition", "attachment; filename="+file.getName());
			IOUtils.copy(fis, response.getOutputStream());
			response.flushBuffer();
		} catch (Exception e){
			e.printStackTrace();
			response.getWriter().write(Result.build(ResultType.Failed).convertIntoJSON());
		} finally {
			if (fis != null){
				try {
					fis.close();
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	@ApiOperation(value = "设备导出")
	@GetMapping("/excel/device/{labId}")
	@ResponseBody
	public void get2(HttpServletResponse response, @PathVariable Long labId) throws Exception{
		response.setContentType("application/octet-stream");
		FileInputStream fis = null;
		try {
			File file = new File("Export.xls");

//			写进去Excel
			OutputStream out = new FileOutputStream(file);
			List<ExcelDevicePojo> list = excelDataIO.ExcelDevicePojoExport(labId);
			System.out.println("------------------" + list + "--------------------");

			ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLS, true);
			Sheet sheet = new Sheet(1, 0, ExcelDevicePojo.class, "设备", null);
			sheet.setAutoWidth(true);

			writer.write(list, sheet);
			writer.finish();
			out.close();


			fis = new FileInputStream(file);
			response.setHeader("Content-Disposition", "attachment; filename="+file.getName());
			IOUtils.copy(fis, response.getOutputStream());
			response.flushBuffer();
		} catch (Exception e){
			e.printStackTrace();
			response.getWriter().write(Result.build(ResultType.Failed).convertIntoJSON());
		} finally {
			if (fis != null){
				try {
					fis.close();
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		}
	}


}
