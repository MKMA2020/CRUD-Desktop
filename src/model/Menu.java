package model;

import java.io.Serializable;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
import enumeration.MenuType;

/**
 * Information of a Menu.
 *
 * @author Kerman Rodriguez
 */
@XmlRootElement
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID of the Ingredient.
     */
    private Long id;

    /**
     * Name of the Menu.
     */
    private String name;

    /**
     * Type of the menu.
     */
    private MenuType type;

    /**
     * Menu-recipe relation collection of the menu.
     */
    private Set<Menu_Recipe> menurecipes;
    
    /**
     * @return id of menu
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id of menu
     * @param id of menu
     */
    public void setId_menu(Long id) {
        this.id = id;
    }

    /**
     * @return name of menu
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of menu
     * @param name of menu
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return type of menu
     */
    public MenuType getType() {
        return type;
    }

    /**
     * Sets menu type
     * @param type of menu
     */
    public void setType(MenuType type) {
        this.type = type;
    }

    /**
     * @return relational field with recipes 
     */
    public Set<Menu_Recipe> getMenurecipes() {
        return menurecipes;
    }
    
    /**
     * Defines the relation of menu and recipes
     * @param menurecipes the relation with recipes
     */
    public void setMenurecipes(Set<Menu_Recipe> menurecipes) {
        this.menurecipes = menurecipes;
    }

    //TODO Check whether commentary is necessary.
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    //TODO Checks    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Menu)) {
            return false;
        }
        Menu other = (Menu) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    //TODO Check whether commentary is necessary.
    @Override
    public String toString() {
        return "mkma.entity.Menu[ id=" + id + " ]";
    }

}
