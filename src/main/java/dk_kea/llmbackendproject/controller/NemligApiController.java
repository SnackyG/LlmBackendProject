package dk_kea.llmbackendproject.controller;

import dk_kea.llmbackendproject.service.NemligApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NemligApiController {

    @Autowired
    NemligApiService nemligApiService;

    @GetMapping("/Ingredient")
    public void getIngredient() {
        nemligApiService.getIngredientDTO("Mælk", 2);
    }
}
