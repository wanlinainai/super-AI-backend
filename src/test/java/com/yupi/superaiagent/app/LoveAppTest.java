package com.yupi.superaiagent.app;

import com.yupi.superaiagent.rag.rewriteQueryTransformer.MyRewriteQueryTransformer;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoveAppTest {

    @Resource
    private LoveApp loveApp;

    @Resource
    private MyRewriteQueryTransformer queryTransformer;
    @Test
    void doChat() {
        String chatId = UUID.randomUUID().toString();
        // 第一轮
        String message = "你好，我是唐朝李白";
        String answer = loveApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);

        // 第一轮
        message = "请帮我写一段关于求爱不得的七言绝句";
        answer = loveApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);

        // 第一轮
        message = "我叫什么？之前告诉过你的";
        answer = loveApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithReport() {
        String chatId = UUID.randomUUID().toString();
        String message = "你好，我是唐朝李白, 我想让另一半更加爱我，但我不知道怎么办？";
        LoveApp.LoveReport loveReport = loveApp.doChatWithReport(message, chatId);
        Assertions.assertNotNull(loveReport);
    }

    @Test
    void doChatWithRAG() {
        String chatId = UUID.randomUUID().toString();
        String message = "我已经结婚了，但是婚后关系不太稳定，怎么做？";
        String answer = loveApp.doChatWithRAG(message, chatId);
        Assertions.assertNotNull(answer);
    }
}