package factory;

import manager.MenuManager;
import rest.MenuRESTClient;


/**
 *
 * @author 2dam
 */
public class MenuManagerFACTORY {
    public static MenuManager getMenuManager(){
        return new MenuRESTClient();
    }
    
}