package dk_kea.llmbackendproject.schema;



import dk_kea.llmbackendproject.chat_gpt.Parameters;

import java.util.List;
import java.util.Map;

public class RecipeSchemaAdapter implements Parameters {
    @Override
    public Map<String, Object> getProperties() {
        return Map.of(
                "title", Map.of("type", "string"),
                "servings", Map.of("type", "integer"),
                "ingredients_to_buy", Map.of(
                        "type", "array",
                        "items", Map.of(
                                "type", "object",
                                "properties", Map.of(
                                        "name", Map.of("type", "string"),
                                        "amount", Map.of("type", "number"),
                                        "unit", Map.of(
                                                "type", "string",
                                                "enum", List.of("gram", "milliliter", "liter", "teaspoon", "tablespoon", "piece", "deciliter", "kilogram")
                                        )
                                ),
                                "required", List.of("name", "amount", "unit"), // Add the required fields here
                                "additionalProperties", false // Must be added to all objects in the mapping since additionalProperties must be set to false for each object in the parameters when strict is true for function
                        ),
                        "description", "Core ingredients for the recipe, excluding spices and condiments"
                ),
                "ingredients_at_home", Map.of(
                        "type", "array",
                        "items", Map.of(
                                "type", "object",
                                "properties", Map.of(
                                        "name", Map.of("type", "string"),
                                        "amount", Map.of("type", "number"),
                                        "unit", Map.of(
                                                "type", "string",
                                                "enum", List.of("gram", "milliliter", "liter", "teaspoon", "tablespoon", "piece", "deciliter", "kilogram")
                                        )
                                ),
                                "required", List.of("name", "amount", "unit"), // Add the required fields here
                                "additionalProperties", false // Must be added to all objects in the mapping since additionalProperties must be set to false for each object in the parameters when strict is true for function
                        ),
                        "description", "Common household ingredients like spices and condiments"
                ),
                "steps", Map.of("type", "array", "items", Map.of("type", "string")),
                "prep_time_minutes", Map.of("type", "integer"),
                "cook_time_minutes", Map.of("type", "integer"),
                "tags", Map.of("type", "array", "items", Map.of("type", "string"))
        );
    }

    @Override
    public List<String> getRequired() {
        return List.of("title", "servings", "ingredients_to_buy", "ingredients_at_home", "steps", "prep_time_minutes", "cook_time_minutes", "tags"); // Must contain all required fields when strict is true
    }
}