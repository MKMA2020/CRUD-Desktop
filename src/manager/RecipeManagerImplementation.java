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

    public RecipeManagerImplementation() {
        webClient = new RecipeRESTClient();
    }

    /**
     * Returns a collection with all the recipes.
     *
     * @return collection with all the recipes.
     * @throws TimeoutException
     */

    @Override
    public List<Recipe> getAllRecipes() throws TimeoutException {
        List<Recipe> recipes = null;
        try {
            LOGGER.info("RecipeManager: getAllRecipes()");

            recipes = webClient.findAllRecipes(new GenericType<List<Recipe>>() {
            });

            //recipes = (List<Recipe>) webClient.findAllRecipes(Recipe.class);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new TimeoutException();

        }
        return recipes;
    }

    /**
     * Returns a recipe with an specific id.
     *
     * @param id The id of the user that is being searched for.
     * @return The user whose id equals to the parameter.
     */

    @Override
    public Recipe getRecipeById(String id) {
        Recipe recipe = null;
        try {
            LOGGER.info("RecipeManager: getRecipeById()");
            recipe = webClient.find(Recipe.class, id);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
        return recipe;
    }

    /**
     * Creates a new recipe.
     *
     * @param recipe Is the recipe that is going to be created.
     */

    @Override
    public void create(Recipe recipe) {
        try {
            LOGGER.info("Create Recipe");
            webClient.create(recipe);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Create Recipe failed: {0}", ex.getMessage());
        }
    }

    /**
     * Returns all the recipes of an specific type.
     *
     * @param type The type of recipes that is desired to be searched for.
     * @return list of users of the type given in the parameter.
     */

    @Override
    public List<Recipe> getRecipesByType(RecipeType type) {
        List<Recipe> recipes = null;
        try {
            LOGGER.info("RecipeManager: getRecipesByType()");

            recipes = webClient.findRecipesByType(new GenericType<List<Recipe>>() {
            }, type.toString());

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
        return recipes;
    }

    /**
     * Removes an already existing recipe.
     *
     * @param id The id of the recipe to be removed
     */

    @Override
    public void remove(Long id) {
        try {
            LOGGER.info("Remove Recipe");
            webClient.remove(id.toString());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Remove Recipe failed: {0}", ex.getMessage());
        }
    }

    /**
     * Updates an already existing recipe.
     *
     * @param recipe Is the recipe that is going to be updated.
     */

    @Override
    public void update(Recipe recipe) {
        try {
            LOGGER.log(Level.INFO, "Edit User");
            webClient.edit(recipe, recipe.getId().toString());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Edit Recipe failed: {0}", ex.getMessage());
        }
    }

}
