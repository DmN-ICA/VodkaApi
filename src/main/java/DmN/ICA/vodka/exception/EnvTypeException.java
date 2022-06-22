package DmN.ICA.vodka.exception;

public class EnvTypeException extends RuntimeException {
    public EnvTypeException(String message) {
        super(message);
    }

    public EnvTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnvTypeException(Throwable cause) {
        super(cause);
    }

    protected EnvTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
