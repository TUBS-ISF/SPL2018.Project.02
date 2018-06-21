package de.neominik.caval.evaluator;

import de.neominik.caval.lang.*;
import de.neominik.caval.lang.collections.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class Evaluator {

    public final java.util.Map<Symbol, Object> env = new HashMap<>();
    private final Evaluator parentContext;
    public final Set<Symbol> specialForms = new HashSet<>();

    public Evaluator(Evaluator parentContext) {
        this.parentContext = parentContext;
    }

    public static Evaluator createChildEvaluator(Evaluator parent, java.util.Map<Symbol, Object> localBindings) {
        Evaluator eval = new Evaluator(parent);
        eval.env.putAll(localBindings);
        return eval;
    }

    public Object evaluate(Object form) {
        Object result;
        if (form instanceof List && ((List) form).count() != 0) {
            List l = (List) form;
            IFn fn;
            Collection<Object> args;
            if (l.first() instanceof Symbol) {
                final Symbol sym = (Symbol) l.first();
                if (isSpecial(sym)) {
                    args = l.rest().conj(this);
                } else {
                    args = l.rest().stream().map(this::evaluate).collect(Collectors.toList());
                }
                fn = (IFn) resolve(sym);
            } else {
                Object function = evaluate(l.first());
                fn = function instanceof Symbol ? (IFn) resolve((Symbol) function) : (IFn) function;
                args = l.rest().stream().map(this::evaluate).collect(Collectors.toList());
            }
            result = fn != null ? fn.applyTo(args) : null;
        } else if (form instanceof Vector) {
            result = PersistentVector.of(((Vector) form).stream().map(this::evaluate).toArray());
        } else if (form instanceof Map) {
            result = PersistentMap.of(((Map) form).stream().flatMap(e -> ((Vector) e).stream()).map(this::evaluate).toArray());
        } else if (form instanceof Symbol) {
            result = resolve((Symbol) form);
        } else {
            result = form;
        }
        return result;
    }

    private boolean isSpecial(Symbol sym) {
        if (parentContext != null) {
            return parentContext.isSpecial(sym);
        }
        return specialForms.contains(sym);
    }

    private Object resolve(Symbol sym) {
        return env.containsKey(sym) ? env.get(sym) : (parentContext != null ? parentContext.resolve(sym) : null);
    }
}
