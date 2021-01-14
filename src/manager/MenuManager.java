/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import enumeration.MenuType;
import java.util.List;
import model.Menu;

/**
 *
 * @author Kerman
 */
public interface MenuManager {
    public List<Menu> findAll();
    public List<Menu> findByType(MenuType type);
    public Menu find(Long id);
    public void delete(Long id);
    public void create(Menu menu);
    public void edit(Menu menu);
}
