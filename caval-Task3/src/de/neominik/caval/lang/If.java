// #condition If
package de.neominik.caval.lang;

import de.neominik.caval.evaluator.Evaluator;

public class If extends AFn {

    @Override
    public Object invoke(Object evaluator, Object test, Object then) {
        return invoke(evaluator, test, then, null);
    }

    @Override
    public Object invoke(Object evaluator, Object test, Object then, Object elze) {
        Evaluator eval = (Evaluator) evaluator;
        if (Logic.truthy(eval.evaluate(test))) {
            return eval.evaluate(then);
        } else {
            return eval.evaluate(elze);
        }
    }

}
