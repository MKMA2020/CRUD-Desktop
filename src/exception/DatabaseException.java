package exception;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Exception triggered with an error during the reading.
 *
 * @author Martin Gros
 */
public class DatabaseException extends Exception {

    public DatabaseException() {
        final Logger LOG = Logger.getLogger("exception.DatabaseException");
        LOG.log(Level.SEVERE, "Unexpected response from the server");
    }
}
