package edu.uni.labManagement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/05/03 22:38
 */
@Api(description = "文件上传模块")
@Controller
@RequestMapping("json/labManagement/fileUpload")
public class UploadController {
	@ApiOperation(value = "上传图片文件")
	@PostMapping("/uploadPicture")
	@ResponseBody
	public void receive(
			@ApiParam(value = "上传的文件", required = true)MultipartFile multipartFile,
			HttpServletResponse response) throws IOException {
//		if(){
			System.out.println(multipartFile.getContentType());
			if (multipartFile != null ){
				String path = ResourceUtils.getURL("classpath:").getPath() + "/upload/picture";
				String originName = UUID.randomUUID() + multipartFile.getOriginalFilename();
				multipartFile.transferTo(new File(path, originName));
				response.getWriter().write(Result.build(ResultType.Success).appendData("path", path).appendData("fileName", originName).convertIntoJSON());
			}else{
				response.getWriter().write(Result.build(ResultType.ParamError).convertIntoJSON());
			}
//		}
	}
}
