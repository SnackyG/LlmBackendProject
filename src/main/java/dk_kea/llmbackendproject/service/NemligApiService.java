package dk_kea.llmbackendproject.service;

import dk_kea.llmbackendproject.model.IngredientDTO;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class NemligApiService {
    private final WebClient webClient;

    public NemligApiService() {
        this.webClient = WebClient.create("https://www.nemlig.com/webapi/s/0/1/0/Search/Search?");
    }

    public Mono<IngredientDTO> getIngredientDTO(String query, int amount) {
        return webClient.get()
                .uri("query="+query+"&take="+amount) //eksempel -> query=banana&take=5
                .retrieve()
                .bodyToMono(IngredientDTO.class);
    }
}
