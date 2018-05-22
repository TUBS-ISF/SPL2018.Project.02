// #condition Function
package de.neominik.caval.lang;

import de.neominik.caval.evaluator.Evaluator;
import de.neominik.caval.lang.collections.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Fun extends AFn {

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(Object evaluator, Object argv, Object body) {
        Evaluator eval = (Evaluator) evaluator;
        List<Symbol> args = new ArrayList<>((Vector) argv);
        switch (args.size()) {
            case 0:
                return new AFn() {
                    @Override
                    public Object invoke() {
                        return eval.evaluate(body);
                    }
                };
            case 1:
                return new AFn() {
                    @Override
                    public Object invoke(Object arg0) {
                        return Evaluator.createChildEvaluator(eval, mapOf(args.get(0), arg0)).evaluate(body);
                    }
                };
            case 2:
                return new AFn() {
                    @Override
                    public Object invoke(Object arg0, Object arg1) {
                        return Evaluator.createChildEvaluator(eval, mapOf(args.get(0), arg0, args.get(1), arg1)).evaluate(body);
                    }
                };
            case 3:
                return new AFn() {
                    @Override
                    public Object invoke(Object arg0, Object arg1, Object arg2) {
                        return Evaluator.createChildEvaluator(eval, mapOf(args.get(0), arg0, args.get(1), arg1, args.get(2), arg2)).evaluate(body);
                    }
                };
            case 4:
                return new AFn() {
                    @Override
                    public Object invoke(Object arg0, Object arg1, Object arg2, Object arg3) {
                        return Evaluator.createChildEvaluator(eval, mapOf(args.get(0), arg0, args.get(1), arg1, args.get(2), arg2, args.get(3), arg3)).evaluate(body);
                    }
                };
            case 5:
                return new AFn() {
                    @Override
                    public Object invoke(Object arg0, Object arg1, Object arg2, Object arg3, Object arg4) {
                        return Evaluator.createChildEvaluator(eval, mapOf(args.get(0), arg0, args.get(1), arg1, args.get(2), arg2, args.get(3), arg3, args.get(4), arg4)).evaluate(body);
                    }
                };
            default:
                return new AFn() {
                    @Override
                    public Object invoke(Object arg0, Object arg1, Object arg2, Object arg3, Object arg4, Object... rest) {
                        Map<Symbol, Object> localBindings = new HashMap<>();
                        for (int i = 0; i < rest.length; i++) {
                            localBindings.put(args.get(i + 5), rest[i]);
                        }
                        localBindings.putAll(mapOf(args.get(0), arg0, args.get(1), arg1, args.get(2), arg2, args.get(3), arg3, args.get(4), arg4));
                        return Evaluator.createChildEvaluator(eval, localBindings).evaluate(body);
                    }
                };
        }
    }

    private static Map<Symbol, Object> mapOf(Object... args) {
        Map<Symbol, Object> newMap = new LinkedHashMap<>();
        for (int i = 0; i < args.length; i += 2) {
            newMap.put((Symbol) args[i], args[i + 1]);
        }
        return newMap;
    }
}
