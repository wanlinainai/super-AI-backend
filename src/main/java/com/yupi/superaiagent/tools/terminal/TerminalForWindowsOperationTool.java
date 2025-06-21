package com.yupi.superaiagent.tools.terminal;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Zhangxh
 * @Description 终端操作工具（Windows）
 * @date 2025/06/21 9:49
 */
public class TerminalForWindowsOperationTool {
    @Tool(description = "Execute a command in the terminal")
    public String executeTerminalCommand(@ToolParam(description = "Command to execute in the terminal") String command) {
        StringBuilder output = new StringBuilder();
        try {
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
            Process process = builder.start();
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                output.append("Command execution failed with exit code:").append(exitCode);
            }
        } catch (Exception e) {
            output.append("Error executing command:").append(e.getMessage());
        }

        return output.toString();
    }
}
