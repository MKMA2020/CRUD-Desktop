/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import model.Recipe;
import rest.RecipeRESTClient;

/**
 *
 * @author 2dam
 */
public class RecipeManagerImplementation implements RecipeManager {

    private RecipeRESTClient webClient;
    private static final Logger LOGGER = Logger.getLogger("PocketChefDesktop");

    @Override
    public Collection<Recipe> getAllRecipes() {
        List<Recipe> recipes = null;
        try {
            LOGGER.info("RecipeManager: getAllRecipes()");
            
            recipes = webClient.findAllRecipes(new GenericType<List<Recipe>>() {});
            
            //recipes = (List<Recipe>) webClient.findAllRecipes(Recipe.class);
            
        } catch (Exception ex) {

        }
        return recipes;
    }

}
