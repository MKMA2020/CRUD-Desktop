/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import manager.IngredientManagerImplementation;
import manager.IngredientManager;

/**
 *
 * @author Martin Valiente Ainz
 */
public class IngredientManagerFACTORY {
    public static IngredientManager getIngredientManager(){
        return new IngredientManagerImplementation();
    }
    
}