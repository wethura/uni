package edu.uni.labManagement.config;

import edu.uni.config.GlobalConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@PropertySource("classpath:config/labManagement.properties")
//@ConfigurationProperties(prefix="uni.labManagement")
public class LabManagementConfig {
    // 引入总配置类
    @Resource
    private GlobalConfig globalConfig;

    @Value("${nui.labManagement.pageSize}")
    private Integer pageSize;

    // 存储上传的excel文件夹的名称
    @Value("${nui.labManagement.excelDir}")
    private String excelDir;


    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getAbsoluteExcelDir() {
        return globalConfig.getUploadRootDir() + excelDir;
    }

    public String getExcelDir() {
        return excelDir;
    }

    public void setExcelDir(String excelDir) {
        this.excelDir = excelDir;
    }
}
