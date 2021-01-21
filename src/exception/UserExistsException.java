package exception;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Exception triggered by trying to create a user that already exists.
 *
 * @author Kerman Rodríguez
 */
public class UserExistsException extends Exception {

    public UserExistsException() {
        final Logger LOG = Logger.getLogger("exception.UserExistsException");
        LOG.log(Level.WARNING, "User being registered already exists");
    }
}