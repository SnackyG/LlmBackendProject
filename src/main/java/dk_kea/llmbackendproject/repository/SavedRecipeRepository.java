package dk_kea.llmbackendproject.repository;

import dk_kea.llmbackendproject.entity.SavedRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedRecipeRepository extends JpaRepository<SavedRecipe, Long> {
}
