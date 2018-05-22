// #condition Let
package de.neominik.caval.lang;

import de.neominik.caval.evaluator.Evaluator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Binding extends AFn {

    @Override
    public Object invoke(Object evaluator, Object bindings, Object body) {
        Evaluator childEval = createLocalBoundChildEvaluator((List) bindings, evaluator);
        return childEval.evaluate(body);
    }

    @Override
    public Object invoke(Object evaluator, Object bindings, Object body1, Object body2) {
        Evaluator childEval = createLocalBoundChildEvaluator((List) bindings, evaluator);
        childEval.evaluate(body1);
        return childEval.evaluate(body2);
    }

    @Override
    public Object invoke(Object evaluator, Object bindings, Object body1, Object body2, Object body3) {
        Evaluator childEval = createLocalBoundChildEvaluator((List) bindings, evaluator);
        childEval.evaluate(body1);
        childEval.evaluate(body2);
        return childEval.evaluate(body3);
    }

    @Override
    public Object invoke(Object evaluator, Object bindings, Object body1, Object body2, Object body3, Object... bodys) {
        Evaluator childEval = createLocalBoundChildEvaluator((List) bindings, evaluator);
        childEval.evaluate(body1);
        childEval.evaluate(body2);
        childEval.evaluate(body3);
        Arrays.stream(bodys).limit(bodys.length - 1).forEach(childEval::evaluate);
        return childEval.evaluate(bodys[bodys.length - 1]);
    }

    private Evaluator createLocalBoundChildEvaluator(List bindings, Object evaluator) {
        Evaluator eval = (Evaluator) evaluator;
        Map<Symbol, Object> localBindings = new HashMap<>();
        Evaluator childEval = Evaluator.createChildEvaluator(eval, localBindings);
        for (int i = 0; i < bindings.size(); i += 2) {
            final Symbol sym = (Symbol) bindings.get(i);
            final Object value = childEval.evaluate(bindings.get(i + 1));
            localBindings.put(sym, value);
            childEval = Evaluator.createChildEvaluator(eval, localBindings);
        }
        return childEval;
    }
}
