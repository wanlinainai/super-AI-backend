package com.yupi.superaiagent.rag;

import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;

/**
 * @ClassName: LoveAppContextualQueryAugmenterFactory
 * @Author: zxh
 * @Date: 2025/6/19 23:09
 * @Description: 创建上下文增强器的工厂
 */
public class LoveAppContextualQueryAugmenterFactory {
    public static ContextualQueryAugmenter createInstance() {
        PromptTemplate promptTemplate = new PromptTemplate("你应该输出以下内容：" +
                "抱歉，我只能回答恋爱相关的问题，别的问题没有办法帮助到你呢？" +
                "有问题的话请联系：张德彪");
        return ContextualQueryAugmenter.builder()
                .allowEmptyContext(false)
                .emptyContextPromptTemplate(promptTemplate)
                .build();
    }
}
