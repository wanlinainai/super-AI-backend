package com.yupi.superaiagent.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Zhangxh
 * @Description
 * @date 2025/06/21 9:33
 */
public class WebScrapingToolTest {
    @Test
    public void testScrapeWebPage() {
        WebScrapingTool webScrapingTool = new WebScrapingTool();
        String url = "https://www.codefather.cn";
        String result = webScrapingTool.scrapeWebPage(url);
        assertNotNull(result);
    }
}