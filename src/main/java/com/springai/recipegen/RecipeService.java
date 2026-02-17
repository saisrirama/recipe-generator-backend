package com.springai.recipegen;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
// import org.springframework.ai.ollama.OllamaChatModel;
// import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RecipeService {

        private final ChatModel chatModel;

        public RecipeService(ChatModel chatModel) {
                this.chatModel = chatModel;
        }

        public String createRecipe(String ingredients,
                        String cuisine,
                        String dietaryRestrictions) {

                String template = """
                                You are a professional chef. Create a high-quality recipe.

                                Ingredients: {ingredients}
                                Cuisine: {cuisine}
                                Dietary: {dietaryRestrictions}

                                Structure:
                                - Title
                                - Cuisine & Dietary
                                - Ingredients (with quantities)
                                - Instructions (numbered steps)
                                - Chef's Tip

                                Rules:
                                - Strict dietary compliance.
                                - Concise, beginner-friendly.
                                - Use Markdown.
                                """;

                PromptTemplate promptTemplate = new PromptTemplate(template);

                Map<String, Object> params = Map.of(
                                "ingredients", ingredients,
                                "cuisine", cuisine,
                                "dietaryRestrictions", dietaryRestrictions);

                try {
                        ChatResponse response = chatModel.call(
                                        new Prompt(
                                                        promptTemplate.createMessage(params),
                                                        ChatOptions.builder()
                                                                        .temperature(0.7)
                                                                        .build()));

                        if (response == null || response.getResult() == null
                                        || response.getResult().getOutput() == null) {
                                return "AI service returned an empty or invalid response.";
                        }

                        return response.getResult().getOutput().getText();
                } catch (Exception e) {
                        // Re-throw to be caught by GlobalExceptionHandler
                        throw e;
                }
        }
}
