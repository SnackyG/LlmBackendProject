package dk_kea.llmbackendproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddToBasketRequestDTO {
    private String productId;
    private int quantity;
}
