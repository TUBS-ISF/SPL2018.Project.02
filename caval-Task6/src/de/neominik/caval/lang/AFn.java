package de.neominik.caval.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class AFn implements IFn {
    @Override
    public Object invoke() {
        throw new ArityException(0, this);
    }

    @Override
    public Object invoke(Object arg1) {
        throw new ArityException(1, this);
    }

    @Override
    public Object invoke(Object arg1, Object arg2) {
        throw new ArityException(2, this);
    }

    @Override
    public Object invoke(Object arg1, Object arg2, Object arg3) {
        throw new ArityException(3, this);
    }

    @Override
    public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4) {
        throw new ArityException(4, this);
    }

    @Override
    public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
        throw new ArityException(5, this);
    }

    @Override
    public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object... args) {
        throw new ArityException(5 + args.length, this);
    }

    @Override
    public void run() {
        invoke();
    }

    @Override
    public Object call() {
        return invoke();
    }

    @Override
    public Object applyTo(Collection args) {
        Iterator i = args.iterator();
        switch (args.size()) {
            case 0:
                return invoke();
            case 1:
                return invoke(i.next());
            case 2:
                return invoke(i.next(), i.next());
            case 3:
                return invoke(i.next(), i.next(), i.next());
            case 4:
                return invoke(i.next(), i.next(), i.next(), i.next());
            case 5:
                return invoke(i.next(), i.next(), i.next(), i.next(), i.next());
            default:
                @SuppressWarnings("unchecked")
                List l = new ArrayList(args);
                return invoke(i.next(), i.next(), i.next(), i.next(), i.next(), l.subList(5, l.size()));
        }
    }
}
