package dk_kea.llmbackendproject.controller;

import dk_kea.llmbackendproject.model.ProductDTO;
import dk_kea.llmbackendproject.service.NemligApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class NemligApiController {

    @Autowired
    NemligApiService nemligApiService;

    @GetMapping("/Ingredient/{product}/{amount}")
    public ProductDTO getIngredient(@PathVariable String product, @PathVariable int amount) {
        return nemligApiService.getCheapestIngredient(product, amount);
    }

    @GetMapping("/Ingredients/{product}/{amount}")
    public List<ProductDTO> getIngredientsFromNemligBySearchWord(@PathVariable String product, @PathVariable int amount) {
        return nemligApiService.getIngredientsFromNemligBySearchWord(product, amount);
    }
}
