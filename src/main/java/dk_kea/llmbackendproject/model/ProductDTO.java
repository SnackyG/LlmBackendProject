package dk_kea.llmbackendproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDTO {
    @JsonProperty("Id")
    private String id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Brand")
    private String brand;

    @JsonProperty("SubCategory")
    private String subCategory;

    @JsonProperty("UnitPrice")
    private String unitPrice;

    @JsonProperty("Price")
    private double price;
}
