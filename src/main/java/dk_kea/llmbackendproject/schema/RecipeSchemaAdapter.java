package dk_kea.llmbackendproject.schema;

import dk_kea.llmbackendproject.chat_gpt.Parameters;

import java.util.List;
import java.util.Map;

public class RecipeSchemaAdapter implements Parameters {

    private static final List<String> UNIT_ENUM = List.of(
            "gram", "milliliter", "liter", "tsk",
            "spsk", "stk", "deciliter", "kilogram"
    );

    private static Map<String, Object> ingredientSchema() {
        return Map.of(
                "type", "object",
                "properties", Map.of(
                        "name", Map.of("type", "string"),
                        "amount", Map.of("type", "number"),
                        "unit", Map.of(
                                "type", "string",
                                "enum", UNIT_ENUM
                        )
                ),
                "required", List.of("name", "amount", "unit"),
                "additionalProperties", false
        );
    }

    @Override
    public Map<String, Object> getProperties() {
        return Map.of(
                "title", Map.of("type", "string"),
                "servings", Map.of("type", "integer"),
                "ingredients_to_buy", Map.of(
                        "type", "array",
                        "items", ingredientSchema(),
                        "description", "Main ingredients needed for the recipe, excluding common household items like spices and condiments"
                ),
                "ingredients_at_home", Map.of(
                        "type", "array",
                        "items", ingredientSchema(),
                        "description", "Common ingredients typically found at home, such as spices and condiments like mustard, ketchup, salt, or oil"
                ),
                "steps", Map.of(
                        "type", "array",
                        "items", Map.of("type", "string"),
                        "description", "Step-by-step instructions for preparing the recipe"
                ),
                "prep_time_minutes", Map.of("type", "integer"),
                "cook_time_minutes", Map.of("type", "integer"),
                "tags", Map.of(
                        "type", "array",
                        "items", Map.of("type", "string"),
                        "description", "Keywords that describe the recipe, such as cuisine type, dietary tags, or meal category"
                )
        );
    }

    @Override
    public List<String> getRequired() {
        return List.of(
                "title",
                "servings",
                "ingredients_to_buy",
                "ingredients_at_home",
                "steps",
                "prep_time_minutes",
                "cook_time_minutes",
                "tags"
        );
    }
}
