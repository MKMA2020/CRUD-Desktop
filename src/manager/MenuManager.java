/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import enumeration.MenuType;
import exception.DatabaseException;
import java.util.List;
import model.Menu;

/**
 * Interface with the REST methods for the menu entity
 * @author Kerman
 */
public interface MenuManager {
    /**
     * Finds every menu
     * @return a list of every menu
     */
    public List<Menu> findAll();
    /**
     * Finds every menu by a certain type
     * @param type the type to filter the menus
     * @return a list of menus by type
     */
    public List<Menu> findByType(MenuType type);
    /**
     * Finds a menu by id
     * @param id id of the menu
     * @return a menu object
     */
    public Menu find(Long id);
    /**
     * Deletes a menu by id
     * @param id id of the menu
     * @throws DatabaseException if there is an exception with the database 
     */
    public void delete(Long id) throws DatabaseException;
    /**
     *  Creates a menu in the server
     * @param menu menu to create
     * @throws DatabaseException if there is an exception with the database
     */
    public void create(Menu menu)  throws DatabaseException;
    /**
     * Edits a menu
     * @param menu menu to be edited
     * @throws DatabaseException if there is an exception with the database
     */
    public void edit(Menu menu) throws DatabaseException;
}
