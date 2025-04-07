package dk_kea.llmbackendproject.service;

import dk_kea.llmbackendproject.model.Products;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class NemligApiService {
    private final WebClient webClient;

    public NemligApiService() {
        this.webClient = WebClient.create("https://www.nemlig.com/");
    }

    public void getIngredientDTO(String query, int amount) {
        Products productResponseDTO = webClient.get()
                .uri("webapi/s/0/1/0/Search/Search?query=Mælk&take=0")
                .header("Accept", "application/json")
                .retrieve()
                .bodyToMono(Products.class)
                .doOnTerminate(() -> System.out.println("Request completed")) // Optional: for debugging
                .block(); // This will block and wait for the response

        System.out.println(productResponseDTO);
    }
}
