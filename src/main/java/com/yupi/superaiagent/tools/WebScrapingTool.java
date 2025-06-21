package com.yupi.superaiagent.tools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.io.IOException;

/**
 * @author Zhangxh
 * @Description 网页抓取工具
 * @date 2025/06/21 9:30
 */
public class WebScrapingTool {
    /**
     * 网页抓取
     * @param url
     * @return
     */
    @Tool(description = "Scrape the content of the web page")
    public String scrapeWebPage(@ToolParam(description = "URL of the page to scrape") String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            return doc.html();
        } catch (IOException e) {
            return "Error scraping web page" + e.getMessage();
        }
    }
}
