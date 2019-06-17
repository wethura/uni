package edu.uni.labManagement.controller;

import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.labManagement.service.SelfDefineService;
import edu.uni.utils.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/06/08 13:24
 */
@Api(description = "其他组的模块")
@Controller
@RequestMapping("json/labManagement/otherPart")
public class OtherPartController {

	@Autowired
	private SelfDefineService selfDefineService;
	@Autowired
	private RedisCache cache;

	@ApiOperation(value = "查询所有部门")
	@GetMapping("/listAllDepartment")
	@ResponseBody
	public Result get1(HttpServletResponse response) throws Exception{
		List<Map<String, Object>> list = selfDefineService.listAllDepartment();
		if (list == null || list.size() == 0) {
			return Result.build(ResultType.Failed);
		}else {
			return Result.build(ResultType.Success).appendData("res", list);
		}
	}

	@ApiOperation(value = "查询所有部门")
	@GetMapping("/deleteAllCache")
	@ResponseBody
	public Result get2(HttpServletResponse response) throws Exception{
		cache.deleteByPaterm("lm_*");
		return Result.build(ResultType.Success);
	}
}
