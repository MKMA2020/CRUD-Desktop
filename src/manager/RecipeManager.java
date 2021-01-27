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

/**
 *
 * @author Martin Valiente Ainz
 */
public interface RecipeManager {
    
    public Collection<Recipe> getAllRecipes()throws TimeoutException;
    
    public List<Recipe> getRecipesByType (RecipeType type);
    
    public Recipe getRecipeById(String id);
    
    public void create (Recipe recipe);
    
    public void remove(Long id);
   
    
}
