package dk_kea.llmbackendproject.service;

import dk_kea.llmbackendproject.model.NemligApiResponse;

import dk_kea.llmbackendproject.model.ProductDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Service
public class NemligApiService {
    private final WebClient webClient;

    public NemligApiService() {
        this.webClient = WebClient.create("https://www.nemlig.com/");
    }

    public List<ProductDTO> getIngredientDTO(String query, int amount) {
        NemligApiResponse response = webClient.get()
                .uri("webapi/s/0/1/0/Search/Search?query=" + query + "&take=" + amount)
                .header("Accept", "application/json")
                .retrieve()
                .bodyToMono(NemligApiResponse.class)
                .doOnTerminate(() -> System.out.println("Request completed"))
                .block();

        return response != null && response.getProductData() != null
                ? response.getProductData().getProducts()
                : null;
    }
}
