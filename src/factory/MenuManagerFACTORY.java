package factory;

import manager.MenuManager;
import manager.MenuManagerImplementation;


/**
 *
 * @author 2dam
 */
public class MenuManagerFACTORY {
    public static MenuManager getMenuManager(){
        return new MenuManagerImplementation();
    }
    
}