package dk_kea.llmbackendproject.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    private String title;
    private int servings;
    private List<Ingredient> ingredients_to_buy;
    private List<Ingredient> ingredients_at_home;
    private List<String> steps;
    private int prep_time_minutes;
    private int cook_time_minutes;
    private List<String> tags;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Ingredient {
        private String id;
        private String name;
        private String brand;
        private double amount;
        private String unit;
        private double price;


        @Override
        public String toString() {
            return String.format(
                    "    Ingredient {\n" +
                            "      name: '%s',\n" +
                            "      amount: %.2f,\n" +
                            "      unit: '%s'\n" +
                            "    }", name, amount, unit
            );
        }
    }

    public static Recipe fromJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, Recipe.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON to Recipe", e);
        }
    }

    @Override
    public String toString() {
        return "Recipe {\n" +
                "  title: '" + title + "',\n" +
                "  servings: " + servings + ",\n" +
                "  ingredients_to_buy:\n" + formatIngredients(ingredients_to_buy) + ",\n" +
                "  ingredients_at_home:\n" + formatIngredients(ingredients_at_home) + ",\n" +
                "  steps:\n" + formatStringList(steps) + ",\n" +
                "  prep_time_minutes: " + prep_time_minutes + ",\n" +
                "  cook_time_minutes: " + cook_time_minutes + ",\n" +
                "  tags:\n" + formatStringList(tags) + "\n" +
                "}";
    }

    private String formatIngredients(List<Ingredient> ingredients) {
        if (ingredients == null || ingredients.isEmpty()) return "    (none)";
        return ingredients.stream()
                .map(Ingredient::toString)
                .collect(Collectors.joining("\n"));
    }

    private String formatStringList(List<String> list) {
        if (list == null || list.isEmpty()) return "    (none)";
        return list.stream()
                .map(item -> "    - " + item)
                .collect(Collectors.joining("\n"));
    }
}
