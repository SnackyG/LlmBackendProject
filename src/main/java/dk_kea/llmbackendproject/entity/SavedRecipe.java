package dk_kea.llmbackendproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavedRecipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private int servings;
    private int prepTimeMinutes;
    private int cookTimeMinutes;

    @ElementCollection
    private List<String> steps;

    @ElementCollection
    private List<String> tags;

    @OneToMany(cascade = CascadeType.ALL)
    private List<IngredientEntity> ingredientsToBuy;

    @OneToMany(cascade = CascadeType.ALL)
    private List<IngredientEntity> ingredientsAtHome;


}
