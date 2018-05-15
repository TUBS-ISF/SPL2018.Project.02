package de.neominik.caval.lang;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class IO extends AApplyOnlyFn {
    @Override
    @SuppressWarnings("unchecked")
    public Object applyTo(Collection args) {
        Collection<Object> generified = (Collection<Object>) args;
        printFunction().accept(generified.stream().map(Object::toString).collect(Collectors.joining(" ")));
        return null;
    }

    protected abstract Consumer<String> printFunction();

    public static class Println extends IO {
        @Override
        protected Consumer<String> printFunction() {
            return System.out::println;
        }
    }

    public static class Print extends IO {
        @Override
        protected Consumer<String> printFunction() {
            return System.out::print;
        }
    }

    public static class Exit extends AFn {
        @Override
        public Object invoke() {
            return invoke(0);
        }

        @Override
        public Object invoke(Object status) {
            System.exit((Integer) status);
            return null;
        }
    }
}
