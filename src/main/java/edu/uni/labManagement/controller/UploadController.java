//package edu.uni.labManagement.controller;
//
//import edu.uni.bean.Result;
//import edu.uni.bean.ResultType;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//
///**
// * Create by Administrator
// *
// * @author sola
// * @date 2019/05/03 22:38
// */
//@Api(description = "文件上传模块")
//@Controller
//@RequestMapping("json/labManagement/fileUpload")
//public class UploadController {
//	@ApiOperation(value = "创建设备模型")
////	@ApiImplicitParam(name="picture", value = "图片", required = true, dataType = "picture" )
//	@PostMapping(consumes = "multipart/*", headers = "content-type=multipart/form-data")
//	@ResponseBody
//	public Result receive(@ApiParam(value = "上传的文件", required = true)MultipartFile multipartFile){
//
//		return Result.build(ResultType.ParamError);
//	}
//
//}
