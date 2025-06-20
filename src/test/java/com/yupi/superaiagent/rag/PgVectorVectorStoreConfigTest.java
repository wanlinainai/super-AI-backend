package com.yupi.superaiagent.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PgVectorVectorStoreConfigTest {

    @Resource
    VectorStore pgVectorVectorStore;

    // 如果需要在测试中使用ChatModel，可以这样注入
    @Resource
    @Qualifier("dashscopeChatModel")
    private ChatModel dashscopeChatModel;

    @Resource
    @Qualifier("ollamaChatModel")
    private ChatModel ollamaChatModel;

    @Test
    void test() {
        // 相似度查询
        List<Document> results = pgVectorVectorStore
                .similaritySearch(SearchRequest.builder().query("结婚").topK(5).build());
        Assertions.assertNotNull(results);
    }
}
