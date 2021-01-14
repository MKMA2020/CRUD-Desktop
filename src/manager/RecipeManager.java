/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import java.util.Collection;
import model.Recipe;

/**
 *
 * @author Martin Valiente Ainz
 */
public interface RecipeManager {
    
    public Collection<Recipe> getAllRecipes();
    
    public Recipe getRecipeById(String id);
    
}
