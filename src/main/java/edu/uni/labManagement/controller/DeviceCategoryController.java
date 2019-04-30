package edu.uni.labManagement.controller;

import com.github.pagehelper.PageInfo;
import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.example.bean.Product;
import edu.uni.example.controller.ProductController;
import edu.uni.labManagement.bean.DeviceCategory;
import edu.uni.labManagement.service.DeviceCategoryService;
import edu.uni.utils.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/04/30 15:46
 */
@Api( description = "设备分类模块")
@Controller
@RequestMapping("json/labManagement/category")
public class DeviceCategoryController {

	@Autowired
	private DeviceCategoryService deviceCategoryService;

	@Autowired
	private RedisCache cache;

	static class CacheNameHelper{
//		查看目录的所有数据
		private static final String list_all = "lm_deviceCategory_listAll";
	}

	/**
	 * 不需要传参数，调用接口
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("listAll")
	@ApiOperation(value = "查询所有的设备分类")
	public void receive(HttpServletResponse response) throws Exception{

		response.setContentType("application/json;charset=utf-8");
		String cacheName = DeviceCategoryController.CacheNameHelper.list_all;
		String json = cache.get(cacheName);

		if(json == null || json == ""){
			List<DeviceCategory> deviceCategoryList = deviceCategoryService.listAll();
			json = Result.build(ResultType.Success).appendData("res", deviceCategoryList).convertIntoJSON();
			if(deviceCategoryList != null){
				cache.set(cacheName, json);
			}
		}
		response.getWriter().write(json);
	}


}
