package exception;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Exception triggered with an error during the reading.
 *
 * @author Martin Gros
 */
public class TimeoutException extends Exception {

    public TimeoutException() {
        final Logger LOG = Logger.getLogger("exception.TimeoutException");
        LOG.log(Level.SEVERE, "The server didn't respond");
    }
}
