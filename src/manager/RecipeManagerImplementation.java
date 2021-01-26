package manager;

import enumeration.RecipeType;
import exception.TimeoutException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import model.Recipe;
import rest.RecipeRESTClient;

/**
 *
 * @author Martin Valiente Ainz
 */
public class RecipeManagerImplementation implements RecipeManager {

    private RecipeRESTClient webClient;
    private static final Logger LOGGER = Logger.getLogger("PocketChefDesktop");
    
    public RecipeManagerImplementation(){
        webClient = new RecipeRESTClient();
    }

    @Override
    public List<Recipe> getAllRecipes() throws TimeoutException {
        List<Recipe> recipes = null;
        try {
            LOGGER.info("RecipeManager: getAllRecipes()");
            
            recipes = webClient.findAllRecipes(new GenericType<List<Recipe>>() {});
            
            //recipes = (List<Recipe>) webClient.findAllRecipes(Recipe.class);
            
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new TimeoutException();
            
        }
        return recipes;
    }

    @Override
    public Recipe getRecipeById(String id) {
        Recipe recipe = null;
        try{
            LOGGER.info("RecipeManager: getRecipeById()");
            recipe = webClient.find(Recipe.class, id);
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
        return recipe;
    }

    @Override
    public void create(Recipe recipe) {
        try {
            LOGGER.info("Create Recipe");
            webClient.create(recipe);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Create Recipe failed: {0}", ex.getMessage());
        }
    }

    @Override
    public List<Recipe> getRecipesByType(RecipeType type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(Long id) {
        try {
            LOGGER.info("Remove Recipe");
            webClient.remove(id.toString());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Remove Recipe failed: {0}", ex.getMessage());
        }
    }

}
