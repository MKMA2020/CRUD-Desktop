/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import enumeration.MenuType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import model.Menu;
import rest.MenuRESTClient;


/**
 *
 * @author Kerman
 */
public class MenuManagerImplementation implements MenuManager{
    
    private MenuRESTClient webClient;
    private static final Logger LOGGER=Logger.getLogger("MenuManagerImplementation");
    
    public MenuManagerImplementation() {
        webClient = new MenuRESTClient();
    }
    
    @Override
    public List<Menu> findAll() {
        List<Menu> menus = null;
        try {
            LOGGER.info("Finding every menu.");
            menus = webClient.findAll(new GenericType<List<Menu>>() {});
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error while finding: {0}", ex);
        }
        return menus;      
    }

    @Override
    public List<Menu> findByType(MenuType type) {
        List<Menu> menus = null;
        try {
            LOGGER.log(Level.INFO, "Finding every menu of {0} type", type);
            menus = webClient.findByType(new GenericType<List<Menu>>() {}, type.toString());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error while finding: {0}", ex);
        }
        return menus; 
    }

    @Override
    public Menu find(Long id) {
        Menu menu = null;
        try {
            LOGGER.info("Finding a menu by id.");
            menu = webClient.find(Menu.class, id.toString());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error while finding: {0}", ex);
        }
        return menu;
    }

    @Override
    public void delete(Long id) {
        try {
            LOGGER.info("Deleting a menu by id.");
            webClient.remove(id.toString());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error while deleting: {0}", ex);
        }
    }

    @Override
    public void create(Menu menu) {
        try {
            LOGGER.info("Creating a new menu");
            webClient.create(menu);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error while creating: {0}", ex);
        }
    }

    @Override
    public void edit(Menu menu) {
        try {
            LOGGER.info("Editing a menu");
            webClient.edit(menu);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error while editing: {0}", ex);
        }
    }

    
}
