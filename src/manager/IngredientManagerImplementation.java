package manager;

import enumeration.IngredientType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import model.Ingredient;
import rest.IngredientRESTClient;

/**
 * Class implements Ingredient manager Interface using a RESTfult web client.
 *
 * @author Martin Valiente Ainz
 */
public class IngredientManagerImplementation implements IngredientManager {

    private static final Logger LOGGER = Logger.getLogger("IngredientManagerImplementation");
    private IngredientRESTClient webClient;

    /**
     * Constructor creates a IngredientManagerImplementation Object and a
     * webClient instance to use the RESTful services.
     */
    public IngredientManagerImplementation() {
        webClient = new IngredientRESTClient();
    }

    @Override
    public void create(Ingredient ingredient) {
        try {
            LOGGER.info("Create Ingredient");
            webClient.create(ingredient);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Create Ingredient failed: {0}", ex.getMessage());
        }
    }

    @Override
    public Ingredient find(Long id) {
        Ingredient ingredient = null;
        try {
            LOGGER.log(Level.INFO, "Find Ingredient by ID");
            ingredient = webClient.find(Ingredient.class, id.toString());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Find Ingredient by ID failed: {0}", ex.getMessage());
        }
        return ingredient;
    }

    @Override
    public void edit(Ingredient ingredient) {
        try {
            LOGGER.log(Level.INFO, "Edit Ingredient by ID");
            webClient.edit(ingredient, ingredient.getId().toString());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Edit Ingredient by ID failed: {0}", ex.getMessage());
        }
        
    }

    @Override
    public void remove(Long id) {
        try {
            LOGGER.info("Remove Ingredient");
            webClient.remove(id.toString());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Remove Ingredient failed: {0}", ex.getMessage());
        }
    }

    @Override
    public List<Ingredient> findAll() {
        List<Ingredient> ingredients = null;
        try {
            LOGGER.log(Level.INFO, "Find All Ingredients");
            ingredients = webClient.findAll(new GenericType<List<Ingredient>>() {
            });
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Find All Ingredients failed: {0}", ex.getMessage());
        }
        return ingredients;
    }

    @Override
    public List<Ingredient> findAllByType(IngredientType type) {
        List<Ingredient> ingredients = null;
        try {
            LOGGER.log(Level.INFO, "Find Ingredients by Type");
            ingredients = webClient.findByType(new GenericType<List<Ingredient>>() {
            }, type.toString());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Find Ingredients by Type failed: {0}", ex.getMessage());
        }
        return ingredients;
    }

}
