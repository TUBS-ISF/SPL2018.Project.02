import static de.neominik.caval.lang.Arithmetic.toBigDecimal;

import de.neominik.caval.evaluator.Evaluator;
import de.neominik.caval.lang.Logic;
import de.neominik.caval.lang.Symbol;

public aspect GreaterThan {

    public class GreaterThanImpl extends Logic {

        @Override
        protected boolean compareNotFunction(Object previous, Object arg) {
            return toBigDecimal(previous).doubleValue() <= toBigDecimal(arg).doubleValue();
        }
    }

	Evaluator around() : execution(Evaluator Evaluator.constructInitialEvaluator()) {
		Evaluator eval = proceed();
		eval.env.put(Symbol.create(">"), new GreaterThanImpl());
		return eval;
	}
}
