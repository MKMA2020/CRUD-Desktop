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
import model.User;

/**
 *
 * @author Martin Valiente Ainz
 */
public interface UserManager {
   
    public void create(User user);
    
    public User find(Long id);
    
    public void edit(User user);
    
    public void remove(Long id);
    
    public User login(String login, String password)throws IncorrectCredentialsException, TimeoutException;
    
    public User findByName(String fullName);
       
    public List<User> findAll()throws TimeoutException;
    
    public List<User> findByType(UserType type);
    
    public void resetPassword(String login, String email);
}
