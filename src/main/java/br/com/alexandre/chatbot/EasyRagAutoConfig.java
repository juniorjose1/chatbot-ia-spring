package br.com.alexandre.chatbot;

import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EasyRagAutoConfig {

    @Bean
    public ApplicationRunner ingest(EmbeddingModel embeddingModel, EmbeddingStore<TextSegment> embeddingStore) {
        return args -> {

            var ingestor = EmbeddingStoreIngestor.builder()
                    .documentSplitter(DocumentSplitters.recursive(400, 50))
                    .embeddingModel(embeddingModel)
                    .embeddingStore(embeddingStore)
                    .build();

            var parser = new TextDocumentParser();

            var documents = FileSystemDocumentLoader.loadDocuments(
                    "src/main/resources/rag",
                    parser
            );

            ingestor.ingest(documents);
        };
    }

}
