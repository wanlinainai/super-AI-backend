package com.yupi.superaiagent.rag.rewriteQueryTransformer;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.preretrieval.query.transformation.QueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author Zhangxh
 * @Description
 * @date 2025/06/19 14:38
 */
@Component
public class MyRewriteQueryTransformer {

    // 创建查询重写器
    private final QueryTransformer queryTransformer;

    public MyRewriteQueryTransformer(@Qualifier("dashscopeChatModel") ChatModel dashscopeChatModel) {
        ChatClient.Builder builder = ChatClient.builder(dashscopeChatModel);
        // 创建查询重写器
        queryTransformer = RewriteQueryTransformer.builder()
                .chatClientBuilder(builder)
                .build();
    }

    public String doQueryRewrite(String prompt) {
        Query query = new Query(prompt);
        // 执行查询重写
        Query transformQuery = queryTransformer.transform(query);
        // 输出重写之后的查询
        return transformQuery.text();
    }
}
