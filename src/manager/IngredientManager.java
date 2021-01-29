/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import enumeration.IngredientType;
import exception.TimeoutException;
import java.util.List;
import model.Ingredient;

/**
 *Interface for the ingredient manager
 * @author Martin Valiente Ainz
 */
public interface IngredientManager {
    /**
     * Creates a new ingredient
     * @param ingredient that's gonna be added 
     */
    public void create(Ingredient ingredient);
    /**
     * Finds an ingredient by its id
     * @param id
     * @return 
     */
    
    public Ingredient find(Long id);
    /**
     * Edit and updates an ingredient
     * @param ingredient 
     */
    public void edit(Ingredient ingredient);
    /**
     * Removes an ingredient by its id
     * @param id 
     */
    public void remove(Long id);
    /**
     * Method that returns all the ingredients
     * @return
     * @throws TimeoutException 
     */
       
    public List<Ingredient> findAll()throws TimeoutException;
    /**
     * Returns a list of ingredients sorted by their type
     * @param type
     * @return 
     */
    
    public List<Ingredient> findAllByType(IngredientType type);
    
    
}
