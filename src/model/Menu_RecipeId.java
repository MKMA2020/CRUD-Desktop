package model;

import java.io.Serializable;

/**
 * Contains the IDs of the Menu - Recipe relation.
 * 
 * @author Martin Gros
 */
public class Menu_RecipeId implements Serializable {

    private Long menuId;
    private Long recipeId;

    public Menu_RecipeId() {
    }

    public Menu_RecipeId(Long menuId, Long recipeId) {
        this.menuId = menuId;
        this.recipeId = recipeId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }
    
}