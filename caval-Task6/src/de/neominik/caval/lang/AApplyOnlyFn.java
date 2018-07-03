package de.neominik.caval.lang;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class AApplyOnlyFn implements IFn {
    @Override
    public Object invoke() {
        return applyTo(Collections.emptyList());
    }

    @Override
    public Object invoke(Object arg1) {
        return applyTo(Arrays.asList(arg1));
    }

    @Override
    public Object invoke(Object arg1, Object arg2) {
        return Arrays.asList(arg1, arg2);
    }

    @Override
    public Object invoke(Object arg1, Object arg2, Object arg3) {
        return Arrays.asList(arg1, arg2, arg3);
    }

    @Override
    public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4) {
        return Arrays.asList(arg1, arg2, arg3, arg4);
    }

    @Override
    public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
        return Arrays.asList(arg1, arg2, arg3, arg4, arg5);
    }

    @Override
    public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object... args) {
        List<Object> l = new LinkedList<>(Arrays.asList(arg1, arg2, arg3, arg4, arg5));
        l.addAll(Arrays.asList(args));
        return l;
    }

    @Override
    public void run() {
        invoke();
    }

    @Override
    public Object call() {
        return invoke();
    }
}
