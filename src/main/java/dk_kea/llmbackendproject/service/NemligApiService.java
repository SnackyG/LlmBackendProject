package dk_kea.llmbackendproject.service;

import dk_kea.llmbackendproject.model.AddToBasketRequestDTO;
import dk_kea.llmbackendproject.model.Credentials;
import dk_kea.llmbackendproject.mapper.RecipeMapper;
import dk_kea.llmbackendproject.model.NemligApiResponse;

import dk_kea.llmbackendproject.model.ProductDTO;
import dk_kea.llmbackendproject.model.Recipe;
import dk_kea.llmbackendproject.repository.SavedRecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
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
                .doOnTerminate(() -> System.out.println("Request completed"))
                .block();


        return response != null && response.getProductData() != null
                ? response.getProductData().getProducts()
                : null;
    }

    public Mono<ResponseEntity<String>> addToBasket(String productId, int quantity, HttpServletRequest request, HttpServletResponse httpServletResponse) {
        String cookies = request.getHeader("Cookie");

        return webClient.post()
                .uri("webapi/basket/AddToBasket")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header(HttpHeaders.COOKIE, cookies)  // Pass cookies here
                .body(BodyInserters.fromValue(new AddToBasketRequestDTO(productId, quantity)))
                .exchangeToMono(response -> {
                    List<String> moreCookies = response.headers().header("Set-Cookie");

                    // Iterate over the cookies and add each one to the response
                    for (String cookie : moreCookies) {
                        httpServletResponse.addHeader("Set-Cookie", cookie);
                    }
                    // Return a success response
                    return Mono.just(ResponseEntity.ok(""));
                });
    }


    public Mono<ResponseEntity<String>> login(Credentials credentials, HttpServletResponse httpServletResponse) {
        return webClient.post()
                .uri("webapi/login/login")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .bodyValue(new Credentials(credentials.getUsername(), credentials.getPassword()))
                .exchangeToMono(response -> {
                    // Get the 'Set-Cookie' headers from the response
                    List<String> cookies = response.headers().header("Set-Cookie");

                    // Iterate over the cookies and add each one to the response
                    for (String cookie : cookies) {
                        httpServletResponse.addHeader("Set-Cookie", cookie);
                    }

                    // Return a success response
                    return Mono.just(ResponseEntity.ok(""));
                });
    }
}
