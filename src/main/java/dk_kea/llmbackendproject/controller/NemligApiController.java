package dk_kea.llmbackendproject.controller;

import dk_kea.llmbackendproject.model.AddToBasketRequestDTO;
import dk_kea.llmbackendproject.model.Credentials;
import dk_kea.llmbackendproject.model.ProductDTO;
import dk_kea.llmbackendproject.service.NemligApiService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Credentials credentials, HttpServletResponse httpServletResponse) {
        return nemligApiService.login(credentials, httpServletResponse).block();
    }

    @PostMapping("/addToBasket")
    public ResponseEntity<String> addToBasket(@RequestBody AddToBasketRequestDTO addProduct, HttpServletRequest request, HttpServletResponse httpServletResponse) {
        return nemligApiService.addToBasket(addProduct.getProductId(), addProduct.getQuantity(), request, httpServletResponse).block();
    }
}
