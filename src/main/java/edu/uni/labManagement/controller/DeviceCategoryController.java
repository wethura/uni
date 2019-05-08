package edu.uni.labManagement.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageInfo;
import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.example.bean.Product;
import edu.uni.example.controller.ProductController;
import edu.uni.example.service.CategoryService;
import edu.uni.labManagement.bean.DeviceCategory;
import edu.uni.labManagement.service.DeviceCategoryService;
import edu.uni.utils.JsonUtils;
import edu.uni.utils.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
	 * 查询所有的数据
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("listAll")
	@ApiOperation(value = "查询所有的设备分类")
	@ResponseBody
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

	/**
	 * 根据id更新设备类型
	 * @param deviceCategory
	 * @return
	 */
	@ApiOperation(value="根据设备类型id修改设备类型")
//	@ApiImplicitParam(name="deviceCategory", value = "设备类型实体类", required = true, dataType = "deviceCategory")
	@PutMapping
	@ResponseBody
	public Result update(@RequestBody(required = false) DeviceCategory deviceCategory){
		if(deviceCategory != null && deviceCategory.getId() != null){
			boolean success = deviceCategoryService.update(deviceCategory);
			if(success){
				cache.deleteByPaterm(CacheNameHelper.list_all);
				return Result.build(ResultType.Success);
			}else{
				return Result.build(ResultType.Failed);
			}
		}
		return Result.build(ResultType.ParamError);
	}

	/**
	 * 创建设备分类实体
	 * @param deviceCategory
	 * @return
	 */
	@ApiOperation(value = "创建设备类型")
//	@ApiImplicitParam(name = "deviceCategory", value = "设备类型实体类", required = true, dataType = "deviceCategory")
	@PostMapping
	@ResponseBody
	public Result create(@RequestBody(required = false) DeviceCategory deviceCategory){
		if(deviceCategory != null){
			boolean success = deviceCategoryService.insert(deviceCategory);
			if(success) {
				cache.deleteByPaterm(CacheNameHelper.list_all);
				return Result.build(ResultType.Success);
			} else {
				return Result.build(ResultType.Failed);
			}
		}
		return  Result.build(ResultType.ParamError);
	}

	/**
	 * 删除设备分类
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据ID删除设备类型")
//	@ApiImplicitParam(name = "id", value = "设备类型id", required = true, dataType = "Integer", paramType = "path")
	@DeleteMapping("{id}")
	@ResponseBody
	public Result destroy(@PathVariable Integer id){
		boolean success = deviceCategoryService.deleted(id);
		if(success) {
			cache.deleteByPaterm(CacheNameHelper.list_all);
			return Result.build(ResultType.Success);
		}else{
			return Result.build(ResultType.Failed);
		}
	}
}
