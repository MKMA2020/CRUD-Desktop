/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;
import enumeration.RecipeType;
import exception.TimeoutException;
import java.util.Collection;
import java.util.List;
import model.Recipe;
import model.User;

/**
 * Interface for the methods for recipe management.
 * @author Martin Valiente Ainz
 */
public interface RecipeManager {
    
    /**
     * Returns a collection with all the recipes.
     * @return collection with all the recipes.
     * @throws TimeoutException 
     */
    public Collection<Recipe> getAllRecipes()throws TimeoutException;
    
    /**
     * Returns all the recipes of an specific type.
     * @param type The type of recipes that is desired to be searched for.
     * @return list of users of the type given in the parameter.
     */
    public List<Recipe> getRecipesByType (RecipeType type);
    
    /**
     * Returns a recipe with an specific id.
     * @param id The id of the user that is being searched for.
     * @return The user whose id equals to the parameter.
     */
    public Recipe getRecipeById(String id);
    
    /**
     * Creates a new recipe.
     * @param recipe Is the recipe that is going to be created.
     */
    public void create (Recipe recipe);
    
    /**
     * Updates an already existing recipe.
     * @param recipe Is the recipe that is going to be updated.
     */
    public void update (Recipe recipe);
    
    /**
     * Removes an already existing recipe.
     *
     * @param id The id of the recipe to be removed
     */
    public void remove(Long id);
   
    
}
