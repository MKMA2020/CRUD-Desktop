/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import exception.TimeoutException;
import java.util.Collection;
import model.Recipe;

/**
 *
 * @author Martin Valiente Ainz
 */
public interface RecipeManager {
    
    public Collection<Recipe> getAllRecipes()throws TimeoutException;
    
    public Recipe getRecipeById(String id);
    
    public void create (Recipe recipe);
   
    
}
