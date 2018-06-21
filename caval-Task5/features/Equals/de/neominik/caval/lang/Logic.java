package de.neominik.caval.lang;

import java.util.Collection;

import static de.neominik.caval.lang.Arithmetic.toBigDecimal;

public abstract class Logic extends AApplyOnlyFn {
    public static class Equals extends Logic {

        @Override
        protected boolean compareNotFunction(Object a, Object b) {
            if (a == b) return false;
            if (a instanceof Number && b instanceof Number)
                return toBigDecimal(a).doubleValue() != toBigDecimal(b).doubleValue();
            return !a.equals(b);
        }
    }
}
