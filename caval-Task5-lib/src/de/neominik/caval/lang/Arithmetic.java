package de.neominik.caval.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Collection;

public abstract class Arithmetic extends AApplyOnlyFn {
    static BigDecimal toBigDecimal(Object x) {
        if (x instanceof BigDecimal)
            return (BigDecimal) x;
        else if (x instanceof BigInteger)
            return new BigDecimal((BigInteger) x);
        else if (x instanceof Double)
            return new BigDecimal(((Number) x).doubleValue());
        else if (x instanceof Float)
            return new BigDecimal(((Number) x).doubleValue());
        else
            return BigDecimal.valueOf(((Number) x).longValue());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object applyTo(Collection args) {
        Collection<Object> arguments = (Collection<Object>) args;
        return arguments.stream().reduce(this::operation).orElseGet(this::getIdentity);
    }

    abstract Object operation(Object a, Object b);

    abstract Object getIdentity();

    public static class Plus extends Arithmetic {

        Object operation(Object a, Object b) {
            if (a instanceof Integer && b instanceof Integer) return ((Integer) a) + ((Integer) b);
            if (a instanceof Double && b instanceof Double) return ((Double) a) + ((Double) b);
            return toBigDecimal(a).add(toBigDecimal(b)).doubleValue();
        }

        @Override
        Object getIdentity() {
            return 0;
        }
    }

    public static class Minus extends Arithmetic {
        @Override
        public Object applyTo(Collection args) {
            if (args.size() == 1) {
                return operation(0, args.iterator().next());
            }
            return super.applyTo(args);
        }

        Object operation(Object a, Object b) {
            if (a instanceof Integer && b instanceof Integer) return ((Integer) a) - ((Integer) b);
            if (a instanceof Double && b instanceof Double) return ((Double) a) - ((Double) b);
            return toBigDecimal(a).subtract(toBigDecimal(b)).doubleValue();
        }

        @Override
        Object getIdentity() {
            throw new ArityException(0, this);
        }
    }

    public static class Times extends Arithmetic {

        Object operation(Object a, Object b) {
            if (a instanceof Integer && b instanceof Integer) return ((Integer) a) * ((Integer) b);
            if (a instanceof Double && b instanceof Double) return ((Double) a) * ((Double) b);
            return toBigDecimal(a).multiply(toBigDecimal(b)).doubleValue();
        }

        @Override
        Object getIdentity() {
            return 1;
        }
    }

    public static class Divide extends Arithmetic {
        @Override
        public Object applyTo(Collection args) {
            if (args.size() == 1) {
                return operation(1, args.iterator().next());
            }
            return super.applyTo(args);
        }

        Object operation(Object a, Object b) {
            if (a instanceof Integer && b instanceof Integer) return ((Integer) a) / ((Integer) b);
            if (a instanceof Double && b instanceof Double) return ((Double) a) / ((Double) b);
            return toBigDecimal(a).divide(toBigDecimal(b), MathContext.DECIMAL64).doubleValue();
        }

        @Override
        Object getIdentity() {
            throw new ArityException(0, this);
        }
    }
}
