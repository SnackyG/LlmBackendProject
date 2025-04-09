package dk_kea.llmbackendproject.controller;

import dk_kea.llmbackendproject.entity.SavedRecipe;
import dk_kea.llmbackendproject.model.Recipe;
import dk_kea.llmbackendproject.repository.SavedRecipeRepository;
import dk_kea.llmbackendproject.service.ChatGPTRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@CrossOrigin(origins = "http://localhost:63343")
public class RecipeController {

    private final ChatGPTRequestService chatGPTRequestService;
    @Autowired
    private final SavedRecipeRepository savedRecipeRepository;
    @PostMapping("/save-recipe")
    public ResponseEntity<SavedRecipe> saveRecipe(@RequestBody Recipe recipe) {
        SavedRecipe savedRecipe = new SavedRecipe();
        savedRecipe.setTitle(recipe.getTitle());
        savedRecipe.setDescription(recipe.getDescription());
        // Tilføj flere felter hvis din Recipe har dem

        SavedRecipe saved = savedRecipeRepository.save(savedRecipe);
        return ResponseEntity.ok(saved);
    }

    @Autowired
    public RecipeController(ChatGPTRequestService chatGPTRequestService,
                            SavedRecipeRepository savedRecipeRepository) {
        this.chatGPTRequestService = chatGPTRequestService;
        this.savedRecipeRepository = savedRecipeRepository;
    }

    @GetMapping("/generate-recipe")
    public Mono<ResponseEntity<Recipe>> generateRecipe(
            @RequestParam String query,
            @RequestParam(defaultValue = "1.3") double temperature,
            @RequestParam(defaultValue = "0.8") double topP) {

        return chatGPTRequestService.generateRecipeWithSchema(query, temperature, topP)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).build()));
    }

    @GetMapping("/generate-random-recipe")
    public Mono<ResponseEntity<Recipe>> generateRandomRecipe() {
        List<String> categories = List.of(
                "vegansk", "vegetarisk", "med fisk", "med oksekød", "med svinekød",
                "med linser", "asiatisk", "mexicansk", "italiensk", "med aubergine"
        );

        String randomCategory = categories.get(ThreadLocalRandom.current().nextInt(categories.size()));
        String randomQuery = "Lav en " + randomCategory + " ret til aftensmad";

        double temperature = ThreadLocalRandom.current().nextDouble(1.0, 1.8);
        double topP = ThreadLocalRandom.current().nextDouble(0.75, 1.0);

        return chatGPTRequestService.generateRecipeWithSchema(randomQuery, temperature, topP)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).build()));
    }

}
