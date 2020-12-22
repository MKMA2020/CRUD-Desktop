package factory;

import manager.MenuRecipeManager;
import rest.MenuRecipesRESTClient;


/**
 *
 * @author 2dam
 */
public class MenuRecipeManagerFACTORY {
    public static MenuRecipeManager getMenuRecipeManager(){
        return new MenuRecipesRESTClient();
    }
    
}