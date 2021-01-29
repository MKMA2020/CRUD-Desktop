package manager;

import enumeration.UserType;
import exception.IncorrectCredentialsException;
import exception.TimeoutException;
import java.util.List;
import model.User;

/**
 * User Manager interface encapsulating user methods.
 * @author Martin Valiente Ainz
 */
public interface UserManager {
    
    /**
     * Method calls webCliente and calls for creation of user.
     * @param user The user to be created 
     */
    public void create(User user);
    
    /**
     * Method calls webClient and calls for find of user.
     * @param id the User id.
     * @return the found user.
     */
    public User find(Long id);
    
    /**
     * Method calls webClient and calls for edit of user.
     * @param user The user to be edited.
     */
    public void edit(User user);
    
    /**
     * Method calls webCliente and calls for deletion of user.
     * @param id The user to be deleted.
     */
    public void remove(Long id);
    
    /**
     * Method calls webClient and calls for login of user.
     * @param login the login String.
     * @param password the password String.
     * @return The logged User.
     * @throws IncorrectCredentialsException if written User does not exist.
     * @throws TimeoutException If server is down.
     */
    public User login(String login, String password)throws IncorrectCredentialsException, TimeoutException;
    
    /**
     * Method calls webCient and calls for User by Name.
     * @param fullName The full name of the User.
     * @return The User.
     */
    public User findByName(String fullName);
    
    /**
     * Method calls webClient and calls for all users.
     * @return a List of Users.
     * @throws TimeoutException if server is down. 
     */       
    public List<User> findAll()throws TimeoutException;
    
    public List<User> findByType(UserType type);
    
    /**
     * Method calls webClient and calls for a password reset.
     * @param login the login String.
     * @param email the email String.
     */
    public void resetPassword(String login, String email);
}
