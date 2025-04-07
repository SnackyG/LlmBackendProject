package dk_kea.llmbackendproject.model;

public record IngredientDTO(
        String name,
        String brand,
        String subCategory, //Hvilken kategori varen er i - f.eks. "skummetmælk"
        String unitPrice,
        double price
) {
}
