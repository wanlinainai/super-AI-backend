package com.yupi.superaiagent.demo.invoke;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @ClassName: OllamaAiInvoke
 * @Author: zxh
 * @Date: 2025/6/8 23:03
 * @Description: ollama 调用
 */
@Component
public class OllamaAiInvoke implements CommandLineRunner {

    @Resource
    private ChatModel ollamaChatModel;
    @Override
    public void run(String... args) throws Exception {
//        AssistantMessage output = ollamaChatModel.call(new Prompt("你好，我是李白，请帮我生成一段诗歌，必须是七言绝句"))
//                .getResult()
//                .getOutput();
//        System.out.println(output.getText());
    }
}
