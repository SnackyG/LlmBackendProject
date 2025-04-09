package dk_kea.llmbackendproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class SavedRecipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private int servings;
    private int prepTimeMinutes;
    private int cookTimeMinutes;

    private String description;


    @ElementCollection
    private List<String> steps;

    @ElementCollection
    private List<String> tags;

    @OneToMany(cascade = CascadeType.ALL)
    private List<IngredientEntity> ingredientsToBuy;

    @OneToMany(cascade = CascadeType.ALL)
    private List<IngredientEntity> ingredientsAtHome;


}
