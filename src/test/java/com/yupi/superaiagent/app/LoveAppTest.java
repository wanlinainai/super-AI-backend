package com.yupi.superaiagent.app;

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
}