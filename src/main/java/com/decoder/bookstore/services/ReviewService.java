package com.decoder.bookstore.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ChatClient chatClient;

    public ReviewService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String generateReview(String title) {
        try {
            String review = chatClient.prompt()
                    .user("Escreva em português um resumo objetivo e direto do livro '" + title
                            + "' com base apenas no seu conhecimento. Não inclua opiniões, não repita o título "
                            + "e use no máximo 500 caracteres.")
                    .call()
                    .content();

            if (review == null) {
                return null;
            }

            if (review.length() > 500) {
                return review.substring(0, 500);
            }

            return review;
        } catch (Exception exception) {
            System.out.println("Erro ao gerar o review; esse erro precisa ser tratado.");
            // Aqui caberia tratamento adequado, envio para fila de erro, retentativa ou circuit breaker.
            return null;
        }
    }
}
