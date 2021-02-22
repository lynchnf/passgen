package norman.passgen.ui;

import org.slf4j.Logger;

public class LoggingException extends Exception {
    public LoggingException(Logger logger, String message) {
        super(message);
        logger.error(message);
    }

    public LoggingException(Logger logger, String message, Throwable cause) {
        super(message, cause);
        logger.error(message, cause);
    }
}
