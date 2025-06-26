package com.yupi.superaiagent.config;

import com.yupi.superaiagent.tools.WebSearchTool;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.execution.DefaultToolExecutionExceptionProcessor;
import org.springframework.ai.tool.execution.ToolExecutionExceptionProcessor;
import org.springframework.ai.tool.resolution.ToolCallbackResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Zhangxh
 * @Description 重写工具抛出异常逻辑
 * @date 2025/06/24 16:21
 */
@Configuration
public class ToolCallingConfiguration {

    @Bean
    public ToolExecutionExceptionProcessor toolExecutionExceptionProcessor() {
        return new DefaultToolExecutionExceptionProcessor(true);
    }

    // 重写异常处理逻辑
    @Bean
    public ToolExecutionExceptionProcessor customExceptionProcessor(){
        return exception -> {
            if (exception.getCause() instanceof IOException) {
                return "An file process error occurred while processing the request: " + exception.getCause().getMessage();
            } else {
                throw exception;
            }
        };
    }

    @Bean
    public ToolCallbackResolver toolCallbackResolver() {
        HashMap<String, ToolCallback> toolMap = new HashMap<>();
        toolMap.put("weatherTool", new WeatherToolCallback());
        toolMap.put("timeTool", new TimeToolCallback());

        return toolName -> toolMap.get(toolName);
    }
}
