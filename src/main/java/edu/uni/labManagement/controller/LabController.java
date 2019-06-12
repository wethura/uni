package edu.uni.labManagement.controller;

import com.github.pagehelper.PageInfo;
import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.labManagement.bean.Lab;
import edu.uni.labManagement.pojo.LabPojo;
import edu.uni.labManagement.service.LabService;
import edu.uni.labManagement.service.SelfDefineService;
import edu.uni.utils.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/05/09 23:49
 */
@Api(description = "实验室模块")
@Controller
@RequestMapping("json/labManagement/lab")
@Transactional(rollbackFor = {Exception.class})
public class LabController {
	@Autowired
	private RedisCache cache;
	@Autowired
	private LabService labService;
	@Autowired
	private SelfDefineService selfDefineService;

	static class CacheNameHelper{
//		lm_lab_listByPageNum_{pageNum}
		private static final String listByPageNum = "lm_lab_listByPageNum_";
		// lm_lab_{实验室id}
		private static final String base = "lm_lab_*";
		// lm_labs_list_{页码}
		private static final String List_CacheNamePrefix = "lm_lab_list_";
		// lm_labs_listByTwo
		private static final String ListByTwo_CacheName = "lm_lab_listByTwo";
	}

	@ApiOperation(value = "分页查询实验室")
	@GetMapping("listByPage/{pageNum}")
	@ResponseBody
	public void receive(HttpServletResponse response, @PathVariable int pageNum) throws Exception{
		response.setContentType("application/json;charset=utf-8");

		String cacheName = CacheNameHelper.listByPageNum + pageNum;
		cache.delete(cacheName);
		String json = cache.get(cacheName);

		if(json == null || json == "") {
			PageInfo<LabPojo> pageInfo = labService.selectPage(pageNum);
			json = Result.build(ResultType.Success).appendData("pageInfo", pageInfo).convertIntoJSON();
			if (json != null) {
				cache.set(cacheName, json);
			}
		}
		response.getWriter().write(json);
	}

	@ApiOperation(value = "通过实验室ID查询实验室")
	@GetMapping("listByLabId/{labId}")
	@ResponseBody
	public void receive2(HttpServletResponse response, @PathVariable int labId) throws Exception{
		response.setContentType("application/json;charset=utf-8");

		LabPojo labPojo = labService.selectById(labId);
		if(labPojo != null && labPojo.getDeleted() == false) {
			response.getWriter().write(Result.build(ResultType.Success).appendData("res", labPojo).convertIntoJSON());
		} else {
			response.getWriter().write(Result.build(ResultType.Failed).convertIntoJSON());
		}
	}

	@ApiOperation(value = "添加实验室", notes = "已测试")
	@PostMapping
	@ResponseBody
	public Result create(Lab lab, String[] admins){

		if(lab != null && (lab.getName() != null && lab.getName() != "" )) {
			boolean success = labService.insert(lab);
			success = success && selfDefineService.insertAdmins(admins, lab.getId());
 			if (success) {
				cache.deleteByPaterm(CacheNameHelper.base);
				return Result.build(ResultType.Success);
			} else {
				return Result.build(ResultType.Failed);
			}
		}
		return Result.build(ResultType.ParamError);
	}

	@ApiOperation(value = "根据实验室ID删除实验室", notes = "待测试")
	@DeleteMapping("/{labId}")
	@ResponseBody
	public Result destory(@PathVariable long labId) {
		if (labService.deleted(labId)) {
			cache.deleteByPaterm(CacheNameHelper.base);
			return Result.build(ResultType.Success);
		}
		return Result.build(ResultType.Failed);
	}

	/**
	 * 查询所有实验室的id和name
	 * @author 招黄轩
	 */
	@ApiOperation(value = "查询所有实验室的id和name", notes = "")
	@GetMapping("labs/listByTwo")
	public void listByTwo(HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		String cacheName = CacheNameHelper.ListByTwo_CacheName;
		String json = cache.get(cacheName);
		if(json == null){
			List<Map<String,Object>> list = labService.selectByTwo();
			json = Result.build(ResultType.Success).appendData("list", list).convertIntoJSON();
			if(list != null){
				cache.set(cacheName, json);
			}
		}
		response.getWriter().write(json);
	}

	/**
	 * 删除实验室listByTwo的缓存
	 * @return Result
	 * @author 招黄轩
	 */
	@ApiOperation(value = "删除实验室listByTwo的缓存", notes = "")
	@DeleteMapping("labs/listByTwo")
	@ResponseBody
	public Result destroyByTwo(){
		cache.deleteByPaterm(CacheNameHelper.base);
		return Result.build(ResultType.Success);
	}

	@ApiOperation(value = "修改实验室", notes = "")
	@PutMapping("/update")
	@ResponseBody
	public void update(HttpServletResponse response, Lab lab)throws Exception{
		response.setContentType("application/josn;charset=utf-8;");
		if (lab.getId() == null){
			response.getWriter().write(Result.build(ResultType.ParamError).convertIntoJSON());
			return;
		}
		boolean success = labService.update(lab);
		if (success) {
			cache.delete(CacheNameHelper.base);
			response.getWriter().write(Result.build(ResultType.Success).convertIntoJSON());
			return;
		} else {
			response.getWriter().write(Result.build(ResultType.Failed).convertIntoJSON());
			return;
		}
	}

	/**
	 * 指定实验室管理员
	 * @param json 请求参数
	 * @return Result
	 * @author 招黄轩
	 */
	@ApiOperation(value="指定实验室管理员")
	@ApiImplicitParam(name="json", value = "请求参数", required = true, dataType = "Map")
	@PutMapping("assignAdmin")
	@ResponseBody
	public Result assignAdmin(@RequestBody(required = false) Map<String,Object> json){
		if(json != null){
			boolean success = labService.assignAdmin(json);
			if(success){
				cache.deleteByPaterm(CacheNameHelper.base);
				return Result.build(ResultType.Success);
			}else{
				return Result.build(ResultType.Failed);
			}
		}
		return Result.build(ResultType.ParamError);
	}

	/**
	 * 分页查询所有实验室
	 * @param pageNum 页码
	 * @param pageSize 每页显示条数
	 * @author 招黄轩
	 */
	@ApiOperation(value = "分页查询所有实验室", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name="pageNum", value = "页码", required = true, dataType = "Integer", paramType = "path"),
			@ApiImplicitParam(name="pageSize", value = "每页显示条数", required = true, dataType = "Integer", paramType = "path")
	})
	@GetMapping(value = "labs/list/{pageNum}/{pageSize}")
	public void selectPage(
			@PathVariable Integer pageNum ,
			@PathVariable Integer pageSize,
			HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		PageInfo<Map<String,Object>> pageInfo = labService.selectPage(pageNum, pageSize);
		String json = Result.build(ResultType.Success).appendData("pageInfo", pageInfo).convertIntoJSON();
		response.getWriter().write(json);
	}

	/**
	 * 分页高级筛选所有实验室
	 * @param json 请求参数
	 * @author 招黄轩
	 */
	@ApiOperation(value = "分页高级筛选所有实验室", notes = "")
	@ApiImplicitParam(name= "json", value = "请求参数", required = true, dataType = "Map")
	@PostMapping("labs/filter/all")
	public void filterAll(@RequestBody(required = false) Map<String,Object> json,
							   HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		PageInfo<Map<String,Object>> pageInfo = labService.filterAll(json);
		String res = Result.build(ResultType.Success).appendData("pageInfo", pageInfo).convertIntoJSON();
		response.getWriter().write(res);
	}
}
