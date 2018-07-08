import static de.neominik.caval.lang.Logic.truthy;

import de.neominik.caval.evaluator.Evaluator;
import de.neominik.caval.lang.AFn;
import de.neominik.caval.lang.Symbol;
public aspect Not {

    public class NotImpl extends AFn {
        @Override
        public Object invoke(Object arg1) {
            return !truthy(arg1);
        }
    }

	Evaluator around() : execution(Evaluator Evaluator.constructInitialEvaluator()) {
		Evaluator eval = proceed();
		eval.env.put(Symbol.create("not"), new NotImpl());
		return eval;
	}
}
