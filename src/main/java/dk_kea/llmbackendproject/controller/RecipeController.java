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

    //http://localhost:8080/generate-recipe?query=Lav en opskrift med broccoli
    @GetMapping("/generate-recipe")
    public Mono<ResponseEntity<Recipe>> generateRecipe(
            @RequestParam String query,
            @RequestParam(defaultValue = "1.3") double temperature,
            @RequestParam(defaultValue = "0.8") double topP) {

        System.out.println("Temp value " + temperature);
        System.out.println("Top P value " + topP);

        return chatGPTRequestService.generateRecipeWithSchema(query, temperature, topP) // Pass the query to the service method
                .map(recipe -> ResponseEntity.ok(recipe))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).build())); // Handle error response
    }

    @GetMapping("/generate-random-recipe")
    public Mono<ResponseEntity<Recipe>> generateRandomRecipe() {
        // Hovedingredienser og køkkener
        List<String> ingredients = List.of("kylling", "oksekød", "gris", "fisk", "vegetarisk", "vegansk", "smør");
        List<String> cuisines = List.of("italiensk", "mexicansk", "thai", "indisk", "dansk", "japansk", "mellemøstlig", "fransk", "svensk", "norsk", "finsk");

        // Tilfældige valg
        String chosenIngredient = ingredients.get(ThreadLocalRandom.current().nextInt(ingredients.size()));
        String chosenCuisine = cuisines.get(ThreadLocalRandom.current().nextInt(cuisines.size()));
        double temperature = ThreadLocalRandom.current().nextDouble(1.0, 1.8);
        double topP = ThreadLocalRandom.current().nextDouble(0.75, 1.0);

        // Generér prompten
        String randomQuery = String.format(
                "Lav en %s ret fra det %s køkken.",
                chosenIngredient, chosenCuisine
        );

        // Log valgene
        System.out.println("➡️ Ingrediens: " + chosenIngredient);
        System.out.println("➡️ Køkken: " + chosenCuisine);
        System.out.println("🌡️ Temperatur: " + temperature);
        System.out.println("🎯 Top P: " + topP);

        return chatGPTRequestService.generateRecipeWithSchema(randomQuery, temperature, topP)
                .map(recipe -> ResponseEntity.ok(recipe))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).build()));
    }

}
