package com.yupi.superaiagent.demo.invoke;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @ClassName: SpringAiAiInvoke
 * @Author: zxh
 * @Date: 2025/6/8 22:38
 * @Description: 阿里的模型调用invoke （取消注释即可在SpringBoot项目启动时执行）
 */
@Component
public class SpringAiAiInvoke implements CommandLineRunner {
    @Resource
    private ChatModel dashscopeChatModel;
    @Override
    public void run(String... args) throws Exception {
        // ChatModel
//        AssistantMessage output = dashscopeChatModel.call(new Prompt("你好，我是林彪，请帮我写一周关于6月4号的七言绝句"))
//                .getResult()
//                .getOutput();
//        System.out.println(output.getText());

        // ChatClient
        ChatClient chatClient = ChatClient.builder(dashscopeChatModel)
                .defaultSystem("你是恋爱顾问")
                .build();
        String response = chatClient.prompt().user("你好").call().content();

//        ChatClient.builder(dashscopeChatModel)
//                .defaultAdvisors(
//                        new MessageChatMemoryAdvisor()
//                )


    }
}
