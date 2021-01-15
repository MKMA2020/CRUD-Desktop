/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import enumeration.UserType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import model.User;
import rest.UserRESTClient;

/**
 *
 * @author Martin Valiente Ainz
 */
public class UserManagerImplementation implements UserManager {

    private static final Logger LOGGER = Logger.getLogger("UserManagerImplementation");
    private UserRESTClient webClient;

    /**
     * Constructor creates a UserManagerImplementation Object and a webClient
     * instance to use the RESTful services.
     */
    public UserManagerImplementation() {
        webClient = new UserRESTClient();
    }

    @Override
    public void create(User user) {
        try {
            LOGGER.info("Create User");
            webClient.create(user);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Create User failed: {0}", ex.getMessage());
        }
    }

    @Override
    public User find(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void edit(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User login(String login, String password) {

        User user = null;
        try {
            LOGGER.log(Level.INFO, "Login User");
            user = webClient.login(User.class, login, password);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Login User failed: {0}", ex.getMessage());
        }
        return user;
    }

    @Override
    public User findByName(String fullName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> findAll() {
        List<User> users = null;
        try {
            LOGGER.log(Level.INFO, "Find All Users");
            users = webClient.findAll(new GenericType<List<User>>() {
            });
            users = loadResetField(users);
            
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Find All Users failed: {0}", ex.getMessage());
        }
        return users;
    }
    
    private List<User> loadResetField(List<User> users){
        for (User e:users)
            e.setResetPassword(Boolean.FALSE);
        return users;
    }

    @Override
    public List<User> findByType(UserType Type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
