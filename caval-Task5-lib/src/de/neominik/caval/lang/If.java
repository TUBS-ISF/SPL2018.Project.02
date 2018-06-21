package de.neominik.caval.lang;

import de.neominik.caval.evaluator.Evaluator;

public class If extends AFn {
	static boolean truthy(Object test) {
        return test != null && !(test instanceof Boolean && !(Boolean) test);
    }

    @Override
    public Object invoke(Object evaluator, Object test, Object then) {
        return invoke(evaluator, test, then, null);
    }

    @Override
    public Object invoke(Object evaluator, Object test, Object then, Object elze) {
        Evaluator eval = (Evaluator) evaluator;
        if (truthy(eval.evaluate(test))) {
            return eval.evaluate(then);
        } else {
            return eval.evaluate(elze);
        }
    }

}
