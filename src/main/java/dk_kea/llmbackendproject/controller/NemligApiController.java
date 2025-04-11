package dk_kea.llmbackendproject.controller;

import dk_kea.llmbackendproject.model.AddToBasketDTO;
import dk_kea.llmbackendproject.model.AddToBasketRequestDTO;
import dk_kea.llmbackendproject.model.Credentials;
import dk_kea.llmbackendproject.model.ProductDTO;
import dk_kea.llmbackendproject.service.NemligApiService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class NemligApiController {

    @Autowired
    NemligApiService nemligApiService;

    @GetMapping("/Ingredient/{product}")
    public ProductDTO getIngredient(@PathVariable String product) {

        return nemligApiService.getCheapestIngredient(product, 15);
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
    public Mono<ResponseEntity<AddToBasketDTO>> addToBasket(@RequestBody AddToBasketRequestDTO addProduct, HttpServletRequest request, HttpServletResponse httpServletResponse) {
        return nemligApiService.addToBasket(addProduct.getProductId(), addProduct.getQuantity(), request, httpServletResponse);
    }
}
