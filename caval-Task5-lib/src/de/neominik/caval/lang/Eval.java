package de.neominik.caval.lang;

import de.neominik.caval.evaluator.Evaluator;

public class Eval extends AFn {
    @Override
    public Object invoke(Object evaluator, Object form) {
        Evaluator eval = (Evaluator) evaluator;
        return eval.evaluate(eval.evaluate(form));
    }
}
