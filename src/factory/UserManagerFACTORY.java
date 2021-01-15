package factory;

import manager.UserManager;
import manager.UserManagerImplementation;
import rest.UserRESTClient;

/**
 *
 * @author Martin Valiente Ainz
 */
public class UserManagerFACTORY {
    public static UserManager getUserManager(){
        return new UserManagerImplementation();
    }
    
}
