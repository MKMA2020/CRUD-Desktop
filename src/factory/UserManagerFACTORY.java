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
