/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import enumeration.IngredientType;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Information of an Ingredient.
 *
 * @author Martin Valiente Ainz
 */
@XmlRootElement
public class Ingredient implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID of the Ingredient.
     */
    private Long id;

    /**
     * Name of the Ingredient.
     */
    private String name;

    /**
     * Type of the ingredient
     */
    private IngredientType type;

    /**
     * Defines if the ingredient is verified.
     */
    private boolean verified;

    /**
     * @return Returns the ID of the Ingredient Object.
     */
    public Long getId() {
        return id;
    }

    /**
     * @return Returns the Name of the Ingredient Object.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Returns the Type of the Ingredient Object.
     */
    public IngredientType getType() {
        return type;
    }

    /**
     * @return Returns if the Ingredient is Verified.
     */
    public boolean getVerified() {
        return verified;
    }

    /**
     * This is never used since the id is set automatically on the server
     *
     * @param id id to be set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the name of the ingredient
     *
     * @param name name to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the type of the ingredient
     *
     * @param type type to be set.
     */
    public void setType(IngredientType type) {
        this.type = type;
    }

    /**
     * Verifies the ingredient modifying a boolean
     *
     * @param verified the param to be set.
     */
    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    //TODO Check whether commentary is necessary.
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Compare parameters to their id
     *
     * @param object The object to be compared.
     * @return wether they are equal or not.
     */

    //TODO Check whether commentary is necessary.
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ingredient)) {
            return false;
        }
        Ingredient other = (Ingredient) object;
        if (this.name != null && this.type != null && other.type != null && this.name.equals(other.getName()) && this.type.equals(other.type)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @return the ingredient name
     */
    //TODO Check whether commentary is necessary.
    @Override
    public String toString() {
        return getName();
    }
}
