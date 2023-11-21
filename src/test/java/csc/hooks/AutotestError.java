package csc.hooks;

public class AutotestError extends AssertionError {
    public AutotestError(String message) {
        super(message);
    }

    public AutotestError(Throwable cause) {
        super(cause);
    }

    public AutotestError(String message, Throwable cause) {
        super(message, cause);
    }
}
