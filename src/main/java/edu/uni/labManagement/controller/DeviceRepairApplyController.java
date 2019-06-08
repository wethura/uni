package edu.uni.labManagement.controller;

import com.github.pagehelper.PageInfo;
import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.labManagement.bean.DeviceRepairApply;
import edu.uni.labManagement.pojo.DeviceRepairApplyPojo;
import edu.uni.labManagement.service.DeviceRepairApplyService;
import edu.uni.utils.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/05/03 23:50
 */
@Api(description = "设备维修申请模块")
@Controller
@RequestMapping("json/labManagement/deviceRepair")
public class DeviceRepairApplyController {

	@Autowired
	private RedisCache cache;
	@Autowired
	private DeviceRepairApplyService deviceRepairApplyService;

	static class CacheNameHelper{
		//lm_deviceRepairApply_listByStates_{is_success}
		private static final String list_Status = "lm_deviceRepairApply_listByStates_";
	}

	/**
	 * 查询对应状态所有的数据
	 * @param response
	 * @throws Exception
	 */
	@ApiOperation(value = "通过设备维修申请状态查询")
	@GetMapping("{states}/{pageNum}")
	@ResponseBody
	public void receive(HttpServletResponse response, @PathVariable Integer states, @PathVariable int pageNum) throws Exception{

		response.setContentType("application/json;charset=utf-8");
		String cacheName = CacheNameHelper.list_Status + states + "_" + pageNum;
		String json = cache.get(cacheName);

		if(json == null || json == ""){
			PageInfo<DeviceRepairApplyPojo> pageInfo = deviceRepairApplyService.listByStates(states, pageNum);
			json = Result.build(ResultType.Success).appendData("res", pageInfo).convertIntoJSON();
			if(pageInfo != null){
				cache.set(cacheName, json);
			}
		}
		response.getWriter().write(json);
	}

	/**
	 * 根据id
	 * @param deviceRepairApply
	 * @return
	 */
	@ApiOperation(value="根据设备维修申请id修改设备维修申请, 可以传入部分需要修改的数据，可用于审核用")
	@PutMapping
	@ResponseBody
	public Result update(@RequestBody(required = false) DeviceRepairApply deviceRepairApply){
		if(deviceRepairApply != null && deviceRepairApply.getId() != null){
			boolean success = deviceRepairApplyService.update(deviceRepairApply);
			if(success){
				cache.deleteByPaterm(CacheNameHelper.list_Status + deviceRepairApply.getIsSuccess());
				return Result.build(ResultType.Success);
			}else{
				return Result.build(ResultType.Failed);
			}
		}
		return Result.build(ResultType.ParamError);
	}

	/**
	 * 创建设备维修申请
	 * @param deviceRepairApply
	 * @return
	 */
	@ApiOperation(value = "创建维修申请")
	@PostMapping
	@ResponseBody
	public Result create(@RequestBody(required = false) DeviceRepairApply deviceRepairApply){
		if(deviceRepairApply != null){
			boolean success = deviceRepairApplyService.insert(deviceRepairApply);
			if(success) {
				cache.deleteByPaterm(CacheNameHelper.list_Status + deviceRepairApply.getIsSuccess());
				return Result.build(ResultType.Success);
			} else {
				return Result.build(ResultType.Failed);
			}
		}
		return  Result.build(ResultType.ParamError);
	}

	/**
	 * 通过设备维修id删除设备维修申请
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据ID删除设备类型")
	@DeleteMapping("{id}")
	@ResponseBody
	public Result destroy(@PathVariable Integer id){

		boolean success = deviceRepairApplyService.deleted(id);
		if(success) {
			cache.deleteByPaterm(CacheNameHelper.list_Status + "*");
			return Result.build(ResultType.Success);
		}else{
			return Result.build(ResultType.Failed);
		}
	}

	@ApiOperation(value = "删除缓存")
	@DeleteMapping
	@ResponseBody
	public Result delete(){
		try {
			cache.delete(CacheNameHelper.list_Status);
			return Result.build(ResultType.Success);
		}catch (Exception e){
			return Result.build(ResultType.Failed);
		}
	}
}
