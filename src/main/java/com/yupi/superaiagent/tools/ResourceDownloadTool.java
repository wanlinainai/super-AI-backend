package com.yupi.superaiagent.tools;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.yupi.superaiagent.constant.FileConstant;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.io.File;

/**
 * @author Zhangxh
 * @Description 资源下载工具
 * @date 2025/06/21 10:49
 */
public class ResourceDownloadTool {
    /**
     * 下载资源
     * @return
     */
    @Tool(description = "Download a resource from a given URL")
    public String downloadResource(@ToolParam(description = "URL for the resource to download") String url,
                                   @ToolParam(description = "Name of the save the download resource") String fileName) {
        String fileDir = FileConstant.FILE_SAVE_PATH + "/download";
        String filePath = fileDir + "/" + fileName;
        try {
            // 创建目录
            FileUtil.mkdir(fileDir);
            // 使用HuTool的downloadFile方法下载资源
            HttpUtil.downloadFile(url, new File(filePath));
            return "Resource download successfully to:" + filePath;
        } catch (Exception e) {
            return "Error downloading resource: " + e.getMessage();
        }
    }
}
