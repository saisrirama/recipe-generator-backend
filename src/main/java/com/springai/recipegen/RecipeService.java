package com.springai.recipegen;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RecipeService {

    private final OllamaChatModel chatModel;

    public RecipeService(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String createRecipe(String ingredients,
                               String cuisine,
                               String dietaryRestrictions) {

        String template = """
                Create a detailed recipe using the following inputs:

                Ingredients: {ingredients}
                Cuisine type: {cuisine}
                Dietary restrictions: {dietaryRestrictions}

                The recipe should include:
                1. Recipe title
                2. Ingredients list with quantities
                3. Step-by-step cooking instructions
                """;

        PromptTemplate promptTemplate = new PromptTemplate(template);

        Map<String, Object> params = Map.of(
                "ingredients", ingredients,
                "cuisine", cuisine,
                "dietaryRestrictions", dietaryRestrictions
        );

        Prompt prompt = new Prompt(
                promptTemplate.createMessage(params),
                OllamaOptions.builder()
                        .model("gemma3:4b")
                        .temperature(0.3)   // lower = better consistency
                        .topP(0.9)
                        .numPredict(400)
                        .build()
        );

        return chatModel.call(prompt)
                .getResult()
                .getOutput()
                .getContent();
    }
}
