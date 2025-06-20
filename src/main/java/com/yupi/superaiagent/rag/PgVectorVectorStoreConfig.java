package com.yupi.superaiagent.rag;

import com.yupi.superaiagent.demo.textSplitter.MyTokenTextSplitter;
import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.springframework.ai.vectorstore.pgvector.PgVectorStore.PgDistanceType.COSINE_DISTANCE;
import static org.springframework.ai.vectorstore.pgvector.PgVectorStore.PgIndexType.HNSW;

/**
 * @ClassName: PgVectorVectorStoreConfig
 * @Author: zxh
 * @Date: 2025/6/15 22:18
 * @Description:
 */
@Configuration
public class PgVectorVectorStoreConfig {

    @Resource
    private LoveAppDocumentLoader loveAppDocumentLoader;

    @Resource
    private MyTokenTextSplitter myTokenTextSplitter;

    @Bean
    public VectorStore pgVectorVectorStore(JdbcTemplate jdbcTemplate, EmbeddingModel dashscopeEmbeddingModel) {
//        VectorStore vectorStore = PgVectorStore.builder(jdbcTemplate, dashscopeEmbeddingModel)
//                .dimensions(1536)
//                .distanceType(COSINE_DISTANCE)
//                .indexType(HNSW)
//                .initializeSchema(true)
//                .schemaName("public")
//                .vectorTableName("vector_store")
//                .maxDocumentBatchSize(10000)
//                .build();
//
//        // 加载文档
//        List<Document> documents = loveAppDocumentLoader.loadMarkdowns();
//        vectorStore.add(documents);
//        return vectorStore;

        // 使用分词器
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(dashscopeEmbeddingModel)
                .build();
        // 加载文档
        List<Document> documents = loveAppDocumentLoader.loadMarkdowns();
        // 自主切分
        List<Document> splitDocuments = myTokenTextSplitter.splitCustomized(documents);
        simpleVectorStore.add(splitDocuments);
        return simpleVectorStore;
//        VectorStore vectorStore = PgVectorStore.builder(jdbcTemplate, dashscopeEmbeddingModel)
//                .dimensions(1536)
//                .distanceType(COSINE_DISTANCE)
//                .indexType(HNSW)
//                .initializeSchema(true)
//                .schemaName("public")
//                .vectorTableName("vector_store")
//                .maxDocumentBatchSize(10000)
//                .build();
//
//        // 加载文档
//        List<Document> documents = loveAppDocumentLoader.loadMarkdowns();
//        vectorStore.add(documents);
//        return vectorStore;
    }
}
