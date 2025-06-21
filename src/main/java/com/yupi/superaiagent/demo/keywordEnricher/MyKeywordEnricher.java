package com.yupi.superaiagent.demo.keywordEnricher;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.KeywordMetadataEnricher;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: MyKeywordEnricher
 * @Author: zxh
 * @Date: 2025/6/19 00:35
 * @Description: 提取关键词，并将其作为元数据添加
 */
@Component
public class MyKeywordEnricher {
//    private final ChatModel chatModel;
//
//    public MyKeywordEnricher(ChatModel chatModel) {
//        this.chatModel = chatModel;
//    }
//
//    List<Document> enrichDocuments(List<Document> documents) {
//        KeywordMetadataEnricher enricher = new KeywordMetadataEnricher(this.chatModel, 5);
//        return enricher.apply(documents);
//    }
}
