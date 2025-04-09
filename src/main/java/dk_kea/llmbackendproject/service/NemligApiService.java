package dk_kea.llmbackendproject.service;

import dk_kea.llmbackendproject.mapper.RecipeMapper;
import dk_kea.llmbackendproject.model.NemligApiResponse;

import dk_kea.llmbackendproject.model.ProductDTO;
import dk_kea.llmbackendproject.model.Recipe;
import dk_kea.llmbackendproject.repository.SavedRecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class NemligApiService {
    private final WebClient webClient;

    public NemligApiService() {
        this.webClient = WebClient.create("https://www.nemlig.com/");
    }

    public ProductDTO getCheapestIngredient(String query, int amount) {
        List<ProductDTO> listOfProducts = getIngredientsFromNemligBySearchWord(query, amount);

        if (listOfProducts == null || listOfProducts.isEmpty()) {
            return null;
        }

        ProductDTO productToReturn = listOfProducts.get(0);

        for (ProductDTO product : listOfProducts) {
            if (product.getPrice() < productToReturn.getPrice()) {
                productToReturn = product;
            }
        }

        return productToReturn;
    }

    public List<ProductDTO> getIngredientsFromNemligBySearchWord(String query, int amount) {
        NemligApiResponse response = webClient.get()
                .uri("webapi/s/0/1/0/Search/Search?query=" + query + "&take=" + amount)
                .header("Accept", "application/json")
                .retrieve()
                .bodyToMono(NemligApiResponse.class)
                .block();


        return response != null && response.getProductData() != null
                ? response.getProductData().getProducts()
                : null;
    }

}
