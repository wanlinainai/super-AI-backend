package com.yupi.superaiagent.app;


import com.yupi.superaiagent.rag.rewriteQueryTransformer.MyRewriteQueryTransformer;
//import com.yupi.superaiagent.toolCalling.test.WeatherTools;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.ai.tool.method.MethodToolCallback;
import org.springframework.ai.tool.support.ToolDefinitions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoveAppTest {

    @Resource
    @Qualifier("dashscopeChatModel")
    private ChatModel dashscopeChatModel;

    @Resource
    private LoveApp loveApp;

    @Resource
    private MyRewriteQueryTransformer queryTransformer;
    @Test
    void doChat() {
//        String chatId = UUID.randomUUID().toString();
//        // 第一轮
//        String message = "你好，我是唐朝李白";
//        String answer = loveApp.doChat(message, chatId);
//        Assertions.assertNotNull(answer);
//
//        // 第一轮
//        message = "请帮我写一段关于求爱不得的七言绝句";
//        answer = loveApp.doChat(message, chatId);
//        Assertions.assertNotNull(answer);
//
//        // 第一轮
//        message = "我叫什么？之前告诉过你的";
//        answer = loveApp.doChat(message, chatId);
//        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithReport() {
//        String chatId = UUID.randomUUID().toString();
//        String message = "你好，我是唐朝李白, 我想让另一半更加爱我，但我不知道怎么办？";
//        LoveApp.LoveReport loveReport = loveApp.doChatWithReport(message, chatId);
//        Assertions.assertNotNull(loveReport);
    }

    @Test
    void doChatWithRAG() {
        String chatId = UUID.randomUUID().toString();
        String message = "我已经结婚了，但是婚后关系不太稳定，怎么做？";
        String answer = loveApp.doChatWithRAG(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void testToolCalling() {
        // ChatClient.create(dashscopeChatModel)
        // .prompt("What's the weather in Beijing?")
        // .tools(new WeatherTools())
        // .call();
//        ChatClient.create(dashscopeChatModel)
//        .prompt("What's the weather in Beijing?")
//        .functions("weatherFunctions")
//        .call();

        // todo 函数调用
//        Method method = ReflectionUtils.findMethod(WeatherTools.class, "getWeather", String.class);
//        ToolCallback toolCallback = MethodToolCallback.builder()
//                .toolDefinition(ToolDefinitions.builder(method)
//                        .description("获取指定城市的天气信息")
//                        .build())
//                .toolMethod(method)
//                .toolObject(new WeatherTools())
//                .build();
//        ChatClient.create(dashscopeChatModel)
//                .prompt("What's the weather in Beijing?")
//                .tools(toolCallback)
//                .call();

        // ToDo： 使用工具
        // 得到工具对象
//        ToolCallback[] weatherTools = ToolCallbacks.from(new WeatherTools());
        // 绑定工具到对象
//        ChatOptions chatOptions = ToolCallingChatOptions.builder()
//                .toolCallbacks(weatherTools)
//                .build();
        // 构造Prompt指定对话选项
//        Prompt prompt = new Prompt("今天北京天气怎么样？", chatOptions);
//        dashscopeChatModel.call(prompt);
    }

    // 测试工具链式调用
    @Test
    public void testChatWithTools() {
        // 联网搜索
        testMessage("周末我想去爬珠穆朗玛峰，推荐一下合适的路线，我不要装备，轻装上阵.列举出你搜索的信息所在的网址");
        // 网页抓取
        testMessage("最近身体出现情况了，请帮我在www.github.com上看看有没有解决方案");
        // 资源下载
        testMessage("下载一张适合作为手机壁纸的图片");
        // 终端操作
        testMessage("展示一下当前目录下的所有文件");
        // 文件操作
        testMessage("保存一个我的恋爱档案文档");
        // PDF生成
        testMessage("生成一份：‘清明约会计划’PDF，包含餐厅、活动流程和礼物清单");
    }

    private void testMessage(String message) {
        String chatID = UUID.randomUUID().toString();
        String answer = loveApp.doChatWithTools(message, chatID);
        Assertions.assertNotNull(answer);
    }

}