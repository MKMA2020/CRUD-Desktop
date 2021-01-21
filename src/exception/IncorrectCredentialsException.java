package exception;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Exception triggered by trying to create a user that already exists.
 *
 * @author Kerman Rodríguez
 */
public class IncorrectCredentialsException extends Exception {

    public IncorrectCredentialsException() {
        final Logger LOG = Logger.getLogger("exception.IncorrectCredentialsException");
        LOG.log(Level.WARNING, "User or password are incorrect");
    }
}