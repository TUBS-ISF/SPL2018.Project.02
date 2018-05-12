package de.neominik.caval.lang;

public class ArityException extends RuntimeException {
    private final int arity;
    private final IFn fn;

    ArityException(int arity, IFn fn) {
        this.arity = arity;
        this.fn = fn;
    }

    @Override
    public String getMessage() {
        return "Wrong number of arguments '" + arity + "' to function " + fn;
    }
}
