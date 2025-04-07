package dk_kea.llmbackendproject.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk_kea.llmbackendproject.chat_gpt.ChatGPTRequestReturningJSON;
import dk_kea.llmbackendproject.chat_gpt.ChatGPTResponseFromJSON;
import dk_kea.llmbackendproject.model.Recipe;
import dk_kea.llmbackendproject.schema.RecipeSchemaAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ChatGPTRequestService {
    @Value("${openai.key}")
    private String key;
    private final WebClient webClient = WebClient.create("https://api.openai.com");

    public Mono<Recipe> generateRecipeWithSchema() {

        ChatGPTRequestReturningJSON request = ChatGPTRequestReturningJSON.builder()
                .model("gpt-4o-mini")
                .messages(List.of(
                        new ChatGPTRequestReturningJSON.Message("system", "Return only a recipe in JSON format matching the schema."),
                        new ChatGPTRequestReturningJSON.Message("user", "Give me a quick and child friendly recipe with minced beef and carrots.")
                ))
                .tools(List.of(
                        new ChatGPTRequestReturningJSON.Tool(
                                "function",
                                new ChatGPTRequestReturningJSON.Function("create_recipe", new RecipeSchemaAdapter(), true)
                        )
                ))
                .tool_choice(new ChatGPTRequestReturningJSON.ToolChoice("function", new ChatGPTRequestReturningJSON.ToolChoice.Function("create_recipe")))
                .temperature(0.7)
                .top_p(1.0)
                .max_completion_tokens(3000)
                .build();

        String requestJson;
        try {
            requestJson = new ObjectMapper().writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting request to JSON", e);
        }

        return webClient.post()
                .uri("/v1/chat/completions")
                .header("Authorization", "Bearer " +  key)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                // Can be used to help debug errors
//                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), clientResponse ->
//                        clientResponse.bodyToMono(String.class)
//                                .flatMap(errorBody -> {
//                                    System.err.println("Error body: " + errorBody);
//                                    return Mono.error(new RuntimeException("API Error: " + errorBody));
//                                })
//                )
                .bodyToMono(ChatGPTResponseFromJSON.class)
                .map(response -> {
                            System.out.println(response);
                            return Recipe.fromJson(response.getChoices().get(0).getMessage().getTool_calls().get(0).getFunction().getArguments());
                        }
                );
    }

//    public Mono<Weather> generateWeatherWithSchema() {
//        ChatGPTRequestReturningJSON request = ChatGPTRequestReturningJSON.builder()
//                .model("gpt-3.5-turbo-1106")
//                .messages(List.of(
//                        new ChatGPTRequestReturningJSON.Message("system", "Return only weather information in JSON format matching the schema."),
//                        new ChatGPTRequestReturningJSON.Message("user", "Give me the current weather for New York.")
//                ))
//                .tools(List.of(
//                        new ChatGPTRequestReturningJSON.Tool(
//                                "function",
//                                new ChatGPTRequestReturningJSON.Function("get_weather", new WeatherSchemaAdapter(), true)
//                        )
//                ))
//                .tool_choice(new ChatGPTRequestReturningJSON.ToolChoice("function", new ChatGPTRequestReturningJSON.ToolChoice.Function("get_weather")))
//                .temperature(1.0)
//                .top_p(1.0)
//                .build();
//
//        return webClient.post()
//                .uri("/v1/chat/completions")
//                .header("Authorization", key)
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(request)
//                .retrieve()
//                .bodyToMono(ChatGPTResponseFromJSON.class)
//                .map(response -> {
//                            System.out.println(response);
//                            return Weather.fromJson(response.getChoices().get(0).getMessage().getTool_calls().get(0).getFunction().getArguments());
//                        }
//                );
//    }
}
