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
 *
 * @author Martin Valiente Ainz
 */
public interface IngredientManager {
    
    public void create(Ingredient ingredient);
    
    public Ingredient find(Long id);
    
    public void edit(Ingredient ingredient);
    
    public void remove(Long id);
       
    public List<Ingredient> findAll()throws TimeoutException;
    
    public List<Ingredient> findAllByType(IngredientType type);
    
    
}
