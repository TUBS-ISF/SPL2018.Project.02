package de.neominik.caval.lang;

import java.util.Collection;

import static de.neominik.caval.lang.Arithmetic.toBigDecimal;

public abstract class Logic extends AApplyOnlyFn {
    @Override
    public Object applyTo(Collection args) {
        Object previous = null;
        boolean foundAny = false;
        for (Object arg : args) {
            if (!foundAny) {
                foundAny = true;
                previous = arg;
            } else {
                if (compareNotFunction(previous, arg)) {
                    return false;
                } else {
                    previous = arg;
                }
            }
        }
        if (!foundAny) {
            throw new ArityException(0, this);
        }
        return true;
    }

    protected abstract boolean compareNotFunction(Object previous, Object arg);
}
