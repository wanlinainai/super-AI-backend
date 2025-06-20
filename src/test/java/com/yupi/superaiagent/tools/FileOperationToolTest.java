package com.yupi.superaiagent.tools;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class FileOperationToolTest {
    @Test
    public void testReadFile() {
        FileOperationTool tool = new FileOperationTool();
        String fileName = "读文件.txt";
        String result = tool.readFile(fileName);
        assertNotNull(result);
    }

    @Test
    public void testWriteFile() {
        FileOperationTool tool = new FileOperationTool();
        String fileName = "读文件.txt";
        String content = "Super idol 的笑容都没你的甜";
        String result = tool.writeFile(fileName, content);
        assertNotNull(result);
    }
}