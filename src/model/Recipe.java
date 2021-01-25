/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import enumeration.RecipeType;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Recipe entity class
 * @author Martin Gros
 */

@XmlRootElement
public class Recipe {
    

    /**
     * ID of the Recipe.
     */
    private Long id;

    /**
     * Name of the Recipe.
     */
    private String name;

    /**
     * Steps of the Recipe.
     */
    private String steps;

    /**
     * kCal value of the Recipe.
     */
    private float kcal;

    /**
     * Type of the Recipe.
     */
    private RecipeType type;

    /**
     * Ingredient collection of the recipe.
     */
    private Set<Ingredient> ingredients;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public float getKcal() {
        return kcal;
    }

    public void setKcal(float kcal) {
        this.kcal = kcal;
    }

    public RecipeType getType() {
        return type;
    }

    public void setType(RecipeType type) {
        this.type = type;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }
    
        public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    
        
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recipe)) {
            return false;
        }
        Recipe other = (Recipe) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.name;
    }
}