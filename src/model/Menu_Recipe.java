package model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import enumeration.Menu_recipeType;

/**
 * Relation between Menu and Recipe.
 * 
 * @author Martin Gros
 */

@XmlRootElement
public class Menu_Recipe implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * Id of the relation between a menu and a recipe.
     */
    private Menu_RecipeId id;
    
    /**
     * Recipes contained in the menu.
     */
    private Recipe recipes;
    
    /**
     * Type of the recipe.
     */
    private Menu_recipeType type;

    public Menu_RecipeId getId() {
         return id;
    }

    public void setId(Menu_RecipeId id) {
        this.id = id;
    }

    public Recipe getRecipes() {
        return recipes;
    }

    public void setRecipes(Recipe recipes) {
        this.recipes = recipes;
    }

    public Menu_recipeType getType() {
        return type;
    }

    public void setType(Menu_recipeType type) {
        this.type = type;
    }

    //TODO Check whether commentary is necessary.
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    //TODO Check whether commentary is necessary.
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Menu_Recipe)) {
            return false;
        }
        Menu_Recipe other = (Menu_Recipe) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    //TODO Check whether commentary is necessary.
    @Override
    public String toString() {
        return "mkma.entity.Menu_Recipe[ id=" + id + " ]";
    }
}