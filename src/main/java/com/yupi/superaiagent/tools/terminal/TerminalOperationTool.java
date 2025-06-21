package com.yupi.superaiagent.tools.terminal;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Zhangxh
 * @Description 终端操作工具（通用操作系统，非Windows）
 * @date 2025/06/21 9:42
 */
public class TerminalOperationTool {
    /**
     * 执行终端命令
     * @param command
     * @return
     */
    @Tool(description = "Execute a command in the terminal")
    public String executeTerminalCommand(@ToolParam(description = "Command to execute in the terminal") String command) throws IOException, InterruptedException {
        StringBuilder output = new StringBuilder();
        Process process = Runtime.getRuntime().exec(command);
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            output.append("Command execution failed with exit code:").append(exitCode);
        }
        return output.toString();
    }
}
