package com.yupi.superaiagent.demo.textSplitter;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: MyTokenTextSplitter
 * @Author: zxh
 * @Date: 2025/6/18 23:54
 * @Description:
 */
@Component
public class MyTokenTextSplitter {
    public List<Document> splitDocuments(List<Document> documents) {
        TokenTextSplitter splitter = new TokenTextSplitter();
        return splitter.apply(documents);
    }

    public List<Document> splitCustomized(List<Document> documents) {
        TokenTextSplitter splitter = new TokenTextSplitter(1000, 400, 10, 500, true);
        return splitter.apply(documents);
    }
}
