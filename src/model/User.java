package model;

import java.sql.Timestamp;
import java.util.Set;

/**
 * Information of a registered user.
 * @author Aitor Garcia
 */
public class User {

    private static final long serialVersionUID = 1L;

    /**
     * ID of the User.
     */
    private Long id;

    /**
     * Login String of the User.
     */
    private String login;

    /**
     * E-mail of the User.
     */
    private String email;

    /**
     * Full Name of the User.
     */
    private String fullName;

    /**
     * Status (Active or Inactive) of the User.
     */
    private Boolean status;

    /**
     * Password of the User.
     */
    private String password;

    /**
     * Last Access Timestamp of the User.
     */
    private Timestamp lastAccess;

    /**
     * Last Password Change Timestamp of
     * the User.
     */
    private Timestamp lastsPasswordChange;
    
    /**
     * Type of the user.
     */
    private UserType type;
    
    /**
     * Recipe collection of the user.
     */
    private Set<Recipe> recipes;
    
    /**
     * Menu collection of the user.
     */
    private Set<Menu> menus;
    
    /**
     * Ingredient collection of the user.
     */
    private Set<Ingredient> ingredients;

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getPassword() {
        return password;
    }

    public Timestamp getLastAccess() {
        return lastAccess;
    }

    public Timestamp getLastsPasswordChange() {
        return lastsPasswordChange;
    }

    public UserType getType() {
        return type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLastAccess(Timestamp lastAccess) {
        this.lastAccess = lastAccess;
    }

    public void setLastsPasswordChange(Timestamp lastsPasswordChange) {
        this.lastsPasswordChange = lastsPasswordChange;
    }

    public void setType(UserType type) {
        this.type = type;
    }
    
    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }
    
    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mkma.entity.User[ id=" + id + " ]";
    }
}
