package dk_kea.llmbackendproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class NemligApiResponse {

    @JsonProperty("Products")
    private ProductData productData;

    // Du kan eventuelt tilføje felter til Facets, Suggestions osv., hvis nødvendigt.

    @Getter
    @Setter
    public static class ProductData {
        // Denne nøgle "Products" indeholder selve listen af produkter.
        @JsonProperty("Products")
        private List<ProductDTO> products;

        // De øvrige felter fra JSON-strukturen:
        @JsonProperty("ProductGroupId")
        private Object productGroupId;

        @JsonProperty("Start")
        private int start;

        @JsonProperty("NumFound")
        private int numFound;

        @JsonProperty("ExceptionMessage")
        private String exceptionMessage;
    }
}
