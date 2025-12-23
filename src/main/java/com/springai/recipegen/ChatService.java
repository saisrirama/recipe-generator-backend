package com.springai.recipegen;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final OllamaChatModel chatModel;

    public ChatService(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    // Simple call (no options)
    public String getResponse(String prompt) {
        return chatModel.call(prompt);
    }

    // Call with Ollama options (Gemma 4B)
    public String getResponseOptions(String prompt) {

        ChatResponse response = chatModel.call(
                new Prompt(
                        prompt,
                        OllamaOptions.builder()
                                .model("gemma3:4b")
                                .temperature(0.4)
                                .topP(0.9)
                                .numPredict(256)
                                .build()
                )
        );

        return response.getResult().getOutput().getContent();
    }
}
