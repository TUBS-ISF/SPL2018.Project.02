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

    private final java.util.Map<Symbol, Object> env = new HashMap<>();
    private final Evaluator parentContext;
    private final Set<Symbol> specialForms = new HashSet<>();

    private Evaluator(Evaluator parentContext) {
        this.parentContext = parentContext;
    }

    public static Evaluator constructInitialEvaluator() {
        Evaluator eval = new Evaluator(null);
        eval.env.put(Symbol.create("def"), new Define(eval.env));
        eval.env.put(Symbol.create("let"), new Binding());
        eval.env.put(Symbol.create("fn"), new Fun());
        eval.env.put(Symbol.create("if"), new If());
        eval.env.put(Symbol.create("quote"), new Quote());
        eval.env.put(Symbol.create("eval"), new Eval());
        eval.specialForms.addAll(eval.env.keySet());

        eval.env.put(Symbol.create("+"), new Arithmetic.Plus());
        eval.env.put(Symbol.create("-"), new Arithmetic.Minus());
        eval.env.put(Symbol.create("*"), new Arithmetic.Times());
        eval.env.put(Symbol.create("/"), new Arithmetic.Divide());
        eval.env.put(Symbol.create("conj"), new CollectionOperations.Conjoin());
        eval.env.put(Symbol.create("first"), new CollectionOperations.First());
        eval.env.put(Symbol.create("rest"), new CollectionOperations.Rest());
        eval.env.put(Symbol.create("count"), new CollectionOperations.Count());
        eval.env.put(Symbol.create("not"), new Logic.Not());
        eval.env.put(Symbol.create("="), new Logic.Equals());
        eval.env.put(Symbol.create(">"), new Logic.GreaterThan());
        eval.env.put(Symbol.create(">="), new Logic.GreaterOrEqual());
        eval.env.put(Symbol.create("<"), new Logic.LessThan());
        eval.env.put(Symbol.create("<="), new Logic.LessOrEqual());
        eval.env.put(Symbol.create("println"), new IO.Println());
        eval.env.put(Symbol.create("print"), new IO.Print());
        eval.env.put(Symbol.create("exit"), new IO.Exit());
        return eval;
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
