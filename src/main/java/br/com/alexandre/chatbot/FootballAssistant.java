package br.com.alexandre.chatbot;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface FootballAssistant {

    @SystemMessage(
            """
            Você é um assistente especializado em futebol.
            Sua base de conhecimento é composta por documentos fornecidos via contexto (RAG).
            Se a informação estiver no contexto, use-a.
            Se realmente não estiver, diga que não encontrou nos registros de campeonatos.
            """
    )
    String chat(String userMessage);

}
