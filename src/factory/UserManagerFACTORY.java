package factory;

import manager.UserManager;
import rest.UserRESTClient;

/**
 *
 * @author Martin Valiente Ainz
 */
public class UserManagerFACTORY {
    public static UserManager getUserManager(){
        return new UserRESTClient();
    }
    
}
