package com.yupi.superaiagent.tools.terminal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Zhangxh
 * @Description
 * @date 2025/06/21 10:34
 */
@SpringBootTest
class TerminalForWindowsOperationToolTest {

    @Test
    void executeTerminalCommand() {
        TerminalForWindowsOperationTool tool = new TerminalForWindowsOperationTool();
        String command = "dir";
        String result = tool.executeTerminalCommand(command);
        assertNotNull(result);
    }
}