package com.yupi.superaiagent;

import com.yupi.superaiagent.rag.PgVectorVectorStoreConfig;
import org.springframework.ai.autoconfigure.vectorstore.pgvector.PgVectorStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = PgVectorStoreAutoConfiguration.class)
public class SuperAiAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuperAiAgentApplication.class, args);
    }

}
