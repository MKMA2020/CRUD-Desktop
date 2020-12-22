/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import manager.UserManager;
import rest.UserRESTClient;

/**
 *
 * @author 2dam
 */
public class UserManagerFACTORY {
    public static UserManager getUserManager(){
        return new UserRESTClient();
    }
    
}
