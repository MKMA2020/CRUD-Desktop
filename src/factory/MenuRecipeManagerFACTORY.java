package factory;

import manager.MenuRecipeManager;
import rest.MenuRecipesRESTClient;


/**
 *Returns the implementation
 * @author Martin Valiente
 */
public class MenuRecipeManagerFACTORY {
    public static MenuRecipeManager getMenuRecipeManager(){
        return new MenuRecipesRESTClient();
    }
    
}