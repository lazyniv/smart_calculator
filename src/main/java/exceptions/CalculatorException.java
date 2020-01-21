package exceptions;

public abstract class CalculatorException extends Exception {
    protected CalculatorException(String message) {
        super(message);
    }
}
