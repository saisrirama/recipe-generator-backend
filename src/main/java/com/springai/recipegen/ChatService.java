package com.springai.recipegen;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatModel chatModel;

    public ChatService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    // Simple call (no options)
    public String getResponse(String prompt) {
        return chatModel.call(prompt);
    }

    // Call with HuggingFace options
    public String getResponseOptions(String prompt) {
        try {
            ChatResponse response = chatModel.call(
                    new Prompt(
                            prompt,
                            ChatOptions.builder()
                                    .temperature(0.4)
                                    .topP(0.9)
                                    // .maxTokens(256)
                                    .build()));

            if (response == null || response.getResult() == null || response.getResult().getOutput() == null) {
                return "AI service returned an empty or invalid response.";
            }

            return response.getResult().getOutput().getText();
        } catch (Exception e) {
            // Re-throw to be caught by GlobalExceptionHandler or log specifically
            throw e;
        }
    }
}
