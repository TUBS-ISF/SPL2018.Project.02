package de.neominik.caval.lang;

import java.util.Collection;

import static de.neominik.caval.lang.Arithmetic.toBigDecimal;

public abstract class Logic extends AApplyOnlyFn {
    public static class GreaterThan extends Logic {

        @Override
        protected boolean compareNotFunction(Object previous, Object arg) {
            return toBigDecimal(previous).doubleValue() <= toBigDecimal(arg).doubleValue();
        }
    }
}
