/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import manager.IngredientManagerImplementation;
import manager.IngredientManager;

/**
 * Returns the implementation
 * @author Aitor Garcia
 */
public class IngredientManagerFACTORY {
    public static IngredientManager getIngredientManager(){
        return new IngredientManagerImplementation();
    }
    
}