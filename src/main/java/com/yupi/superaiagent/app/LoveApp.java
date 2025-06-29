package com.yupi.superaiagent.app;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeCloudStore;
import com.fasterxml.jackson.databind.util.PrimitiveArrayBuilder;
import com.yupi.superaiagent.advisor.MyLoggerAdvisor;
import com.yupi.superaiagent.advisor.ReReadingAdvisor;
import com.yupi.superaiagent.chatmemory.FileBasedChatMemory;
import com.yupi.superaiagent.rag.LoveAppRagCustomAdvisorFactory;
import com.yupi.superaiagent.rag.rewriteQueryTransformer.MyRewriteQueryTransformer;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.DefaultContentFormatter;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.generation.augmentation.QueryAugmenter;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.ai.rag.preretrieval.query.transformation.CompressionQueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.TranslationQueryTransformer;
import org.springframework.ai.rag.retrieval.join.ConcatenationDocumentJoiner;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.transformer.SummaryMetadataEnricher;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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

    @Resource
    private VectorStore loveAppVectorStore;
//    @Autowired
//    private ChatClient.Builder chatClientBuilder;

    @Resource
    private MyRewriteQueryTransformer queryTransformer;

    public LoveApp(@Qualifier("dashscopeChatModel") ChatModel dashscopeChatModel) {
        // 初始化基于内存的对话记忆
        // ChatMemory chatMemory = new InMemoryChatMemory();
        String fileDir = System.getProperty("user.dir") + "/tmp/chat-memory";
        ChatMemory chatMemory = new FileBasedChatMemory(fileDir);
        chatClient = ChatClient.builder(dashscopeChatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(chatMemory),
                        // 自定义拦截器，按需开启
                        new MyLoggerAdvisor()
                        // 自定义拦截器，按需开启
                        // , new ReReadingAdvisor()
                )
                .build();
    }

    public String doChat (String message, String chatId){
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

    record LoveReport(String title, List<String> suggestions) {
    }

    /**
     * Ai 恋爱报告功能（结构化输出）
     *
     * @param message
     * @param chatId
     * @return
     */
    public LoveReport doChatWithReport (String message, String chatId){
        LoveReport loveReport = chatClient
                .prompt()
                .system(SYSTEM_PROMPT + "每一次对话生成都要生成恋爱结果，标题是{用户名}的恋爱报告，内容为建议列表")
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 1))
                .call()
                .entity(LoveReport.class);

        log.info("content: {}", loveReport);

        // Builder创建实例
        DefaultContentFormatter formatter = DefaultContentFormatter.builder()
                .withMetadataSeparator("\n")
                .withMetadataTemplate("{key}：{value}")
                .withTextTemplate("{metadata_string}\n\n{content}")
                .withExcludedInferenceMetadataKeys("embedding", "vector_id")
                .withExcludedEmbedMetadataKeys("source_url", "timestamp")
                .build();

        // 使用格式化器处理文档
        // formatter.format(document, MetadataMode.INFERENCE);

        // loveAppVectorStore.write();
        return loveReport;
    }

    public String doChatWithRAG (String message, String chatId){
        // ChatResponse chatResponse = chatClient.prompt()
        // .user(message)
        // .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
        // .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
        // .advisors(new MyLoggerAdvisor())
        // .advisors(new QuestionAnswerAdvisor(loveAppVectorStore))
        // .call()
        // .chatResponse();
        //
        // String content = chatResponse.getResult().getOutput().getText();

        // todo 待删除
        // DocumentRetriever retriever = VectorStoreDocumentRetriever.builder()
        // .vectorStore(vectorStore)
        // .similarityThreshold(0.7)
        // .topK(5)
        // .filterExpression(new FilterExpressionBuilder()
        // .eq("type", "web")
        // .build())
        // .build();
        // // ToDo 待删除
        // Query.builder()
        // .text("谁是Trump?")
        // .context(Map.of(VectorStoreDocumentRetriever.FILTER_EXPRESSION, "type ==
        // 'boy'"))
        // .build();
        // new ConcatenationDocumentJoiner()
        // List<Document> documents = retriever.retrieve(new Query("谁是唐朝李白"));

        // ToDo 待删除
//        Advisor retrivalAugmentationAdvisor = RetrievalAugmentationAdvisor.builder()
//                .documentRetriever(VectorStoreDocumentRetriever.builder()
//                        .similarityThreshold(0.5)
//                        .vectorStore(loveAppVectorStore)
//                        .build())
//                .build();
//        String answer = chatClient.prompt()
//                .advisors(retrivalAugmentationAdvisor)
//                .user("你好")
//                .call()
//                .content();
//        ChatResponse chatResponse = chatClient.prompt()
//                .user(message)
//                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
//                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
//                .advisors(new MyLoggerAdvisor())
//                .advisors(new QuestionAnswerAdvisor(loveAppVectorStore))
//                .call()
//                .chatResponse();
//
//
//        String content = chatResponse.getResult().getOutput().getText();
//        log.info("content: {}", content);
//        return content;
        // todo  查询重写器
        String rewriteMessage = queryTransformer.doQueryRewrite(message);
        ChatResponse chatResponse = chatClient
                .prompt()
                .user(rewriteMessage)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .advisors(new MyLoggerAdvisor())
                .advisors(new QuestionAnswerAdvisor(loveAppVectorStore))
                .advisors(LoveAppRagCustomAdvisorFactory.createLoveAppRagCustomAdvisor(
                        loveAppVectorStore, "已婚"
                ))
                .call()
                .chatResponse();
        String content = chatResponse.getResult().getOutput().getText();
        log.info("content:{}", content);
        // todo 待删除
//        RetrievalAugmentationAdvisor build = RetrievalAugmentationAdvisor.builder()
//                .queryTransformers(RewriteQueryTransformer.builder()
//                        .chatClientBuilder(chatClientBuilder.build().mutate())
//                        .build())
//                .documentRetriever(VectorStoreDocumentRetriever.builder()
//                        .similarityThreshold(0.5)
//                        .vectorStore(loveAppVectorStore)
//                        .build())
//                .build();
//
//        log.info("content: {}", answer);
//        return answer;


        return content;
    }

    @Resource
    private ToolCallback[] allTools;
    public String doChatWithTools(String message, String chatId) {
        ChatResponse response = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                // 开启日志
                .advisors(new MyLoggerAdvisor())
                .tools(allTools)
                .call()
                .chatResponse();
        String content = response.getResult().getOutput().getText();
        log.info("content: {}", content);

        String response = ChatClient.create(chatModel)
                .prompt()
                .tools("WeatherTool", "TimeTool")
                .call()
                .content();
        return content;
    }
}
