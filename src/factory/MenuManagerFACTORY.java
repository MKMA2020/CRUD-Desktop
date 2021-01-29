package factory;

import manager.MenuManager;
import manager.MenuManagerImplementation;


/**
 * Returns the implementation
 * @author Kerman Rodriguez
 */
public class MenuManagerFACTORY {
    public static MenuManager getMenuManager(){
        return new MenuManagerImplementation();
    }
    
}