// #condition Define
package de.neominik.caval.lang;

import de.neominik.caval.evaluator.Evaluator;

import java.util.Map;

public class Define extends AFn {
    private final Map<Symbol, Object> env;

    public Define(Map<Symbol, Object> env) {
        this.env = env;
    }

    @Override
    public Object invoke(Object evaluator, Object sym, Object value) {
        Evaluator eval = (Evaluator) evaluator;
        env.put((Symbol) sym, eval.evaluate(value));
        return sym;
    }
}
