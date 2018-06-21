package de.neominik.caval.lang;

import java.util.Collection;
import java.util.concurrent.Callable;

public interface IFn extends Callable, Runnable {
    Object invoke();

    Object invoke(Object arg1);

    Object invoke(Object arg1, Object arg2);

    Object invoke(Object arg1, Object arg2, Object arg3);

    Object invoke(Object arg1, Object arg2, Object arg3, Object arg4);

    Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5);

    Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object... args);

    Object applyTo(Collection args);
}
