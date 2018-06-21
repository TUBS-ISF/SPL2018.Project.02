package de.neominik.caval.lang;

import java.util.Collection;

import static de.neominik.caval.lang.Arithmetic.toBigDecimal;

public abstract class Logic extends AApplyOnlyFn {
    static boolean truthy(Object test) {
        return test != null && !(test instanceof Boolean && !(Boolean) test);
    }

    public static class Not extends AFn {
        @Override
        public Object invoke(Object arg1) {
            return !truthy(arg1);
        }
    }
}
