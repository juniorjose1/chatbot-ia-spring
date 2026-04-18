package br.com.alexandre.chatbot;

import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.query.Query;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/football", consumes = "text/plain", produces = "text/plain")
public class FootballResource {

    private final FootballAssistant footballAssistant;
    private final ContentRetriever retriever;

    // Injetamos o Retriever para podermos logar o que a IA está fazendo
    public FootballResource(FootballAssistant footballAssistant, ContentRetriever retriever){
        this.footballAssistant = footballAssistant;
        this.retriever = retriever;
    }

    @PostMapping
    public String chat(@RequestBody String userMessage){

        // --- INÍCIO DO DEBUG ---
        System.out.println("=== PERGUNTA DO USUÁRIO ===");
        System.out.println(userMessage);

        var resultados = retriever.retrieve(Query.from(userMessage));

        System.out.println("\n=== O QUE O BANCO DE DADOS ENTREGOU PARA A IA LER ===");
        for (int i = 0; i < resultados.size(); i++) {
            System.out.println("Pedaço " + (i+1) + ":\n" + resultados.get(i).toString() + "\n-");
        }
        System.out.println("======================================================\n");
        // --- FIM DO DEBUG ---

        return footballAssistant.chat(userMessage);
    }

}
