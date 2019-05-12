package edu.uni.labManagement.controller;

import com.github.pagehelper.PageInfo;
import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.labManagement.bean.Lab;
import edu.uni.labManagement.pojo.LabPojo;
import edu.uni.labManagement.service.LabService;
import edu.uni.utils.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/05/09 23:49
 */
@Api(description = "实验室模块")
@Controller
@RequestMapping("json/labManagement/lab")
public class LabController {
	@Autowired
	private RedisCache cache;
	@Autowired
	private LabService labService;

	static class CacheNameHelper{
//		lm_lab_listByPageNum_{pageNum}
		private static final String listByPageNum = "lm_lab_listByPageNum_";
	}

	@ApiOperation(value = "分页查询实验室")
	@GetMapping("listByPage/{pageNum}")
	@ResponseBody
	public void receive(HttpServletResponse response, @PathVariable int pageNum) throws Exception{
		response.setContentType("application/json;charset=utf-8");

		String cacheName = CacheNameHelper.listByPageNum + pageNum;
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

	@ApiOperation(value = "添加实验室", notes = "待测试")
	@PostMapping
	@ResponseBody
	public Result create(Lab lab){
		if(lab != null && (lab.getName() != null && lab.getName() != "" )) {
			if (labService.insert(lab) == true) {
				cache.deleteByPaterm(CacheNameHelper.listByPageNum + "*");
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
			cache.deleteByPaterm(CacheNameHelper.listByPageNum + "*");
			return Result.build(ResultType.Success);
		}
		return Result.build(ResultType.Failed);
	}
}
