package com.yupi.superaiagent.tools;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import dev.langchain4j.agent.tool.P;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: WebSearchTool
 * @Author: zxh
 * @Date: 2025/6/21 00:23
 * @Description: 联网搜索
 */
public class WebSearchTool {
    public static final String SEARCH_URL = "https://www.searchapi.io/api/v1/search";

    private final String apiKey;

    public WebSearchTool(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * 搜索方法
     * @param query
     * @return
     */
    @Tool(description = "Search for informations from baidu Search Engine")
    public String searchWeb(@ToolParam(description = "Search query keyword") String query) {
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("q", query);
        paraMap.put("api_key", apiKey);
        paraMap.put("engine", "baidu");
        try {
            String response = HttpUtil.get(SEARCH_URL, paraMap);
            // 取出前五条
            JSONObject jsonObject = JSONUtil.parseObj(response);
            // 提取organic_results字段
            JSONArray organicResults = jsonObject.getJSONArray("organic_results");
            List<Object> objects = organicResults.subList(0, 5);
            // 拼接搜索结果为字符串
            String result = objects.stream().map(obj -> {
                JSONObject tmpJsonObject = (JSONObject) obj;
                return tmpJsonObject.toString();
            }).collect(Collectors.joining(","));
            return result;
        } catch (Exception e) {
            return "Error searching Baidu: " + e.getMessage();
        }
    }
}
