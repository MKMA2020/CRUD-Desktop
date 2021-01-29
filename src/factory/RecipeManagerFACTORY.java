/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import manager.RecipeManager;
import manager.RecipeManagerImplementation;

/**
 *Returns the manager implementation
 * @author Martin Gros
 */
public class RecipeManagerFACTORY {
    public static RecipeManager getRecipeManager(){
        return new RecipeManagerImplementation();
    }
}
