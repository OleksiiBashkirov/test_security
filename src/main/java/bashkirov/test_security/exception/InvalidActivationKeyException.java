package bashkirov.test_security.exception;

public class InvalidActivationKeyException extends RuntimeException{
    public InvalidActivationKeyException(String message) {
        super(message);
    }
}
