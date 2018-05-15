package de.neominik.caval.lang;

import java.util.Collection;

import static de.neominik.caval.lang.Arithmetic.toBigDecimal;

public abstract class Logic extends AApplyOnlyFn {
    static boolean truthy(Object test) {
        return test != null && !(test instanceof Boolean && !(Boolean) test);
    }

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

    public static class Not extends AFn {
        @Override
        public Object invoke(Object arg1) {
            return !truthy(arg1);
        }
    }

    public static class Equals extends Logic {

        @Override
        protected boolean compareNotFunction(Object a, Object b) {
            if (a == b) return false;
            if (a instanceof Number && b instanceof Number)
                return toBigDecimal(a).doubleValue() != toBigDecimal(b).doubleValue();
            return !a.equals(b);
        }
    }

    public static class GreaterThan extends Logic {

        @Override
        protected boolean compareNotFunction(Object previous, Object arg) {
            return toBigDecimal(previous).doubleValue() <= toBigDecimal(arg).doubleValue();
        }
    }

    public static class GreaterOrEqual extends Logic {

        @Override
        protected boolean compareNotFunction(Object previous, Object arg) {
            return toBigDecimal(previous).doubleValue() < toBigDecimal(arg).doubleValue();
        }
    }

    public static class LessThan extends Logic {

        @Override
        protected boolean compareNotFunction(Object previous, Object arg) {
            return toBigDecimal(previous).doubleValue() >= toBigDecimal(arg).doubleValue();
        }
    }

    public static class LessOrEqual extends Logic {

        @Override
        protected boolean compareNotFunction(Object previous, Object arg) {
            return toBigDecimal(previous).doubleValue() > toBigDecimal(arg).doubleValue();
        }
    }

}
