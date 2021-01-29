/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import enumeration.UserType;
import exception.IncorrectCredentialsException;
import exception.TimeoutException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.GenericType;
import model.User;
import rest.UserRESTClient;

/**
 * This class implements {@link UserManager} business logic interface using a
 * RESTful web client to access users in the server.
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

    /**
     * This method adds a new created User. This is done by sending a POST
     * request to a RESTful web service.
     *
     * @param user The User object to be added.
     */
    @Override
    public void create(User user) {
        try {
            LOGGER.info("Create User");
            webClient.create(user);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Create User failed: {0}", ex.getMessage());
        }
    }
    
    /**
     * This method finds a User. This is done by sending a GET
     * request to a RESTful web service.
     *
     * @param id the id to be search.
     */
    @Override
    public User find(Long id) {
        User user = null;
        try {
            LOGGER.info("Find User");
            user = webClient.find(User.class, id.toString());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Find User failed: {0}", ex.getMessage());
        }
        return user;
    }

    /**
     * This method updates data for an existing User data for user. This is done
     * by sending a PUT request to a RESTful web service.
     *
     * @param user The User object to be updated.
     */
    @Override
    public void edit(User user) {
        try {
            LOGGER.log(Level.INFO, "Edit User");
            webClient.edit(user, user.getId().toString());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Edit User failed: {0}", ex.getMessage());
        }
    }

    /**
     * This method deletes data for an existing user. This is done by sending a
     * DELETE request to a RESTful web service.
     *
     * @param id The User id.
     */
    @Override
    public void remove(Long id) {
        try {
            LOGGER.info("Remove User");
            webClient.remove(id.toString());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Remove User failed: {0}", ex.getMessage());
        }
    }

    /**
     * This method returns a {@link User} matching the login.
     *
     * @param login the login String.
     * @param password the password String.
     * @return the logged User.
     * @throws TimeoutException if there is a connection error.
     * @throws IncorrectCredentialsException if there is no matching user.
     */
    @Override
    public User login(String login, String password) throws TimeoutException, IncorrectCredentialsException {
        User user = null;
        try {
            LOGGER.log(Level.INFO, "Login User");
            user = webClient.login(User.class, login, password);
        } catch (ForbiddenException e) {
            throw new IncorrectCredentialsException();

        } catch (Exception ex) {

            LOGGER.log(Level.SEVERE, "Find All Users failed: {0}", ex.getMessage());
            throw new TimeoutException();
        }
        return user;
    }

    /**
     * This method returns a {@link User} matching the name.
     *
     * @return user The user.
     */
    @Override
    public User findByName(String fullName) {
        User user = null;
        try {
            LOGGER.log(Level.INFO, "Find users by name");
            user = webClient.findByFN(User.class, fullName);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Find users by name failed: {0}", ex.getMessage());
        }
        return user;
    }

    /**
     * This method returns a List of {@link User}, containing all user data.
     *
     * @return users The List of all users.
     * @throws TimeoutException If server is offline.
     */
    @Override
    public List<User> findAll() throws TimeoutException {
        List<User> users = null;
        try {
            LOGGER.log(Level.INFO, "Find All Users");
            users = webClient.findAll(new GenericType<List<User>>() {
            });
            users = loadResetField(users);

        } catch (Exception ex) {

            LOGGER.log(Level.SEVERE, "Find All Users failed: {0}", ex.getMessage());
            throw new TimeoutException();
        }
        return users;
    }

    /**
     * This method assigns false to resetPassword field on all Users
     *
     * @param users the user collection
     * @return the user collection with resetPassword field
     */
    private List<User> loadResetField(List<User> users) {
        for (User e : users) {
            e.setResetPassword(Boolean.FALSE);
        }
        return users;
    }

    /**
     * This method returns a List of {@link User}, containing all user data of
     * the specified type.
     *
     * @param type the type of {@link User} to seach.
     * @return the {@link User} List.
     */
    @Override
    public List<User> findByType(UserType type) {
        List<User> users = null;
        try {
            LOGGER.log(Level.INFO, "FindUserByType");
            users = webClient.findByType(new GenericType<List<User>>() {
            }, type.toString());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Find Users by type failed: {0}", ex.getMessage());
        }
        return users;
    }

    /**
     * This method resets the password to the user.
     *
     * @param login login to reset password.
     * @param email email to reset password.
     */
    @Override
    public void resetPassword(String login, String email) {
        try {
            LOGGER.log(Level.INFO, "Reset password to: {0}", login);
            webClient.resetPassword(login, email);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "ResetPassword to {0} failed: {1}", new Object[]{login, ex.getMessage()});
        }
    }

}
