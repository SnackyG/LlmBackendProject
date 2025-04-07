package dk_kea.llmbackendproject.model;

public record IngredientDTO(
        String Name,
        String Brand,
        String SubCategory, //Hvilken kategori varen er i - f.eks. "skummetmælk"
        String UnitPrice,
        double Price
) {
}
