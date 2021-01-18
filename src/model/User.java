package model;

import enumeration.UserType;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import javafx.beans.property.SimpleBooleanProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Information of a registered user.
 *
 * @author Martin Valiente
 */
@XmlRootElement
public class User {

    private static final long serialVersionUID = 1L;
    
    public User(){
    this.status = new SimpleBooleanProperty();
    this.resetPassword = new SimpleBooleanProperty();
    }

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
    private SimpleBooleanProperty status;

    /**
     * Password of the User.
     */
    private String password;

    /**
     * Last Access Timestamp of the User.
     */
    private Date lastAccess;

    /**
     * Last Password Change Timestamp of the User.
     */
    private Date lastsPasswordChange;

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

    /**
     * Reset password trigger for user.
     */
    private SimpleBooleanProperty resetPassword;

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
        return status.get();
    }

    public String getPassword() {
        return password;
    }

    public Date getLastAccess() {
        return lastAccess;
    }

    public Date getLastsPasswordChange() {
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
        this.status.set(status);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    public void setLastsPasswordChange(Date lastsPasswordChange) {
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

    public Boolean getResetPassword() {
        return this.resetPassword.get();
    }

    public void setResetPassword(Boolean resetPassword) {
        this.resetPassword.set(resetPassword);
    }
    
    //These methods are mandatory for the TableCell and TableView
    //to be able to observe the ObservableValue for changes
    //(see javadoc for PropertyValueFactory). 
    //Finally this allows the table to show the value of the field
    //as the state of the checkbox.
    public SimpleBooleanProperty resetPasswordProperty(){
        return this.resetPassword;
    }
    public SimpleBooleanProperty statusProperty(){
        return this.status;
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
