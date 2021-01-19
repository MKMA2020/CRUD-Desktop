/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import enumeration.UserType;
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
    
    public User login(String login, String password);
    
    public User findByName(String fullName);
       
    public List<User> findAll();
    
    public List<User> findByType(UserType Type);
    
    public void resetPassword(String login, String email);
}
