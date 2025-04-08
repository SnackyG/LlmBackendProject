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

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
    public Mono<ResponseEntity<Recipe>> generateRecipe(
            @RequestParam String query,
            @RequestParam(defaultValue = "1.3") double temperature,
            @RequestParam(defaultValue = "0.8") double topP) {

        return chatGPTRequestService.generateRecipeWithSchema(query, temperature, topP) // Pass the query to the service method
                .map(recipe -> ResponseEntity.ok(recipe))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).build())); // Handle error response
    }

    @GetMapping("/generate-random-recipe")
    public Mono<ResponseEntity<Recipe>> generateRandomRecipe() {
        // Tilfældige kategorier for mere variation
        List<String> categories = List.of(
                "vegansk", "vegetarisk", "med fisk", "med oksekød", "med svinekød",
                "med linser", "asiatisk", "mexicansk", "italiensk", "med aubergine"
        );

        // Vælg en tilfældig kategori
        String randomCategory = categories.get(ThreadLocalRandom.current().nextInt(categories.size()));

        // Byg prompten
        String randomQuery = "Lav en " + randomCategory + " ret til aftensmad";

        // Tilfældige temperatur/top_p værdier
        double temperature = ThreadLocalRandom.current().nextDouble(1.0, 1.8); // mellem 1.0 og 1.8
        double topP = ThreadLocalRandom.current().nextDouble(0.75, 1.0);       // mellem 0.75 og 1.0

        return chatGPTRequestService.generateRecipeWithSchema(randomQuery, temperature, topP)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).build()));
    }

}
