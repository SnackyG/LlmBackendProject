package dk_kea.llmbackendproject.controller;

import dk_kea.llmbackendproject.model.Recipe;
import dk_kea.llmbackendproject.service.ChatGPTRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "http://localhost:63343")
public class RecipeController {

    private final ChatGPTRequestService chatGPTRequestService;

    @Autowired
    public RecipeController(ChatGPTRequestService chatGPTRequestService) {
        this.chatGPTRequestService = chatGPTRequestService;
    }

    // Example endpoint that generates a recipe based on a query (e.g., "red thai curry")
    @GetMapping("/generate-recipe")
    public Mono<ResponseEntity<Recipe>> generateRecipe(@RequestParam String query) {
        return chatGPTRequestService.generateRecipeWithSchema(query) // Pass the query to the service method
                .map(recipe -> ResponseEntity.ok(recipe))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).build())); // Handle error response
    }
}
