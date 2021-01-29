package manager;

import enumeration.IngredientType;
import exception.TimeoutException;
import java.util.List;
import model.Ingredient;

/**
 * IngredientManager interface encapsulating ingredient methods.
 * @author Martin Valiente Ainz
 */
public interface IngredientManager {
    
    /**
     * Method calls webClient and calls for creation of ingredient.
     * @param ingredient The ingredient to be created 
     */
    public void create(Ingredient ingredient);
    
    public Ingredient find(Long id);
    
    public void edit(Ingredient ingredient);
    
    public void remove(Long id);
       
    public List<Ingredient> findAll()throws TimeoutException;
    
    public List<Ingredient> findAllByType(IngredientType type);
    
    
}
