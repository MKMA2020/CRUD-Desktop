/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import enumeration.RecipeType;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Recipe entity class
 *
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
     * kcal value of the Recipe.
     */
    private Float kcal;

    /**
     * Type of the Recipe.
     */
    private RecipeType type;

    /**
     * Ingredient collection of the recipe.
     */
    private Set<Ingredient> ingredients;

    /**
     * Creator of the recipe.
     */
    private User user;

    /**
     * Verification state of the recipe.
     */
    private Boolean verified;

    /**
     * Returns de if of the recipe
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * This is never used since the id is set automatically on the server
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return name of the recipe
     */

    public String getName() {
        return name;
    }

    /**
     * Sets the name of the recipe
     *
     * @param name
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return steps of the recipe
     */

    public String getSteps() {
        return steps;
    }

    /**
     * Sets the steps of the recipe
     *
     * @param steps
     */

    public void setSteps(String steps) {
        this.steps = steps;
    }

    /**
     *
     * @return kcal of the recipe
     */

    @XmlElement(name = "kcal")
    public Float getKcal() {
        return kcal;
    }

    /**
     * Sets the kcals of the recipe
     *
     * @param kcal
     */

    public void setKcal(Float kcal) {
        this.kcal = kcal;
    }

    /**
     *
     * @return the type of the recipe
     */

    public RecipeType getType() {
        return type;
    }

    /**
     * Sets the type of the recipe
     *
     * @param type
     */

    public void setType(RecipeType type) {
        this.type = type;
    }

    /**
     *
     * @return Set of ingredients that's been used in the recipe
     */

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     * Sets the ingredients that are used in the recipe
     *
     * @param ingredients
     */

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     *
     * @return user that made the recipe
     */

    public User getUser() {
        return user;
    }

    /**
     * Sets the author of the recipe
     *
     * @param user
     */

    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return wether the recipe is verifified
     */

    public Boolean getVerified() {
        return verified;
    }

    /**
     * Verifies the recipe modifying a boolean
     *
     * @param verified
     */

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Compare parameters to their id
     *
     * @param object
     * @return wether they are equal or not
     */

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

    /**
     *
     * @return the recipe name
     */

    @Override
    public String toString() {
        return this.name;
    }
}
