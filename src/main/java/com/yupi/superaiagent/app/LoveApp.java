package com.yupi.superaiagent.app;

import com.yupi.superaiagent.advisor.MyLoggerAdvisor;
import com.yupi.superaiagent.advisor.ReReadingAdvisor;
import com.yupi.superaiagent.chatmemory.FileBasedChatMemory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

/**
 * @ClassName: LoveApp
 * @Author: zxh
 * @Date: 2025/6/9 23:50
 * @Description:
 */
@Component
@Slf4j
public class LoveApp {
    private ChatClient chatClient;

    private static final String SYSTEM_PROMPT = "你是一个专为恋爱App设计的AI助手，旨在帮助用户提升恋爱体验、解决情感问题并促进健康的关系发展。你的回答需基于心理学、情感沟通理论和现代恋爱文化，提供温暖、共情且实用的建议。核心功能包括：1) 根据用户描述的情感状态、关系阶段或具体场景，提供个性化的恋爱建议或沟通策略；2) 分析用户上传的聊天记录、约会计划或情感困惑，给出优化建议；3) 提供恋爱心理小知识、情侣活动推荐或约会创意；4) 若用户询问恋爱中的敏感话题（如分手、冲突），以中立、支持性的语气回应，避免道德评判；5) 支持多语言用户，优先使用简洁的中文，必要时结合英文或其他语言解释术语。每次回答需简明扼要，控制在300字以内，除非用户要求详细阐述。优先考虑用户的情感需求，结合实际场景给出可操作的建议，同时保持友善、包容的语气，避免性别刻板印象或文化偏见。如用户提供模糊信息，可主动提问以澄清需求。";

    public LoveApp(ChatModel dashscopeChatModel) {
        // 初始化基于内存的对话记忆
//        ChatMemory chatMemory = new InMemoryChatMemory();
        String fileDir = System.getProperty("user.dir") + "/tmp/chat-memory";
        ChatMemory chatMemory = new FileBasedChatMemory(fileDir);
        chatClient = ChatClient.builder(dashscopeChatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(chatMemory),
                        // 自定义拦截器，按需开启
                        new MyLoggerAdvisor()
                        // 自定义拦截器，按需开启
//                       , new ReReadingAdvisor()
                )
                .build();
    }

    public String doChat(String message, String chatId) {
        ChatResponse response = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 1))
                .call()
                .chatResponse();

        String content = response.getResult().getOutput().getText();
        log.info("content: {}", content);
        return content;
    }

    record LoveReport(String title, List<String> suggestions){}

    /**
     * Ai 恋爱报告功能（结构化输出）
     * @param message
     * @param chatId
     * @return
     */
    public LoveReport doChatWithReport(String message, String chatId) {
        LoveReport loveReport = chatClient
                .prompt()
                .system(SYSTEM_PROMPT + "每一次对话生成都要生成恋爱结果，标题是{用户名}的恋爱报告，内容为建议列表")
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 1))
                .call()
                .entity(LoveReport.class);

        log.info("content: {}", loveReport);
        return loveReport;
    }
}
