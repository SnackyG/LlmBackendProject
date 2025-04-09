package dk_kea.llmbackendproject.mapper;

import dk_kea.llmbackendproject.entity.IngredientEntity;
import dk_kea.llmbackendproject.entity.SavedRecipe;
import dk_kea.llmbackendproject.model.Recipe;

import java.util.stream.Collectors;

public class RecipeMapper {

    public static SavedRecipe toEntity(Recipe recipe) {
        return new SavedRecipe(
                null,
                recipe.getTitle(),
                recipe.getServings(),
                recipe.getPrep_time_minutes(),
                recipe.getCook_time_minutes(),
                recipe.getDescription(),
                recipe.getSteps(),
                recipe.getTags(),
                recipe.getIngredients_to_buy().stream()
                        .map(RecipeMapper::toIngredientEntity)
                        .collect(Collectors.toList()),
                recipe.getIngredients_at_home().stream()
                        .map(RecipeMapper::toIngredientEntity)
                        .collect(Collectors.toList())
        );
    }

    private static IngredientEntity toIngredientEntity(Recipe.Ingredient ingredient) {
        return new IngredientEntity(
                null,
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getBrand(),
                ingredient.getAmount(),
                ingredient.getUnit(),
                ingredient.getPrice()
        );
    }
}
