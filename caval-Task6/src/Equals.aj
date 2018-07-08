import static de.neominik.caval.lang.Arithmetic.toBigDecimal;

import de.neominik.caval.evaluator.Evaluator;
import de.neominik.caval.lang.Logic;
import de.neominik.caval.lang.Symbol;

public aspect Equals {

	public class EqualsImpl extends Logic {

		@Override
		protected boolean compareNotFunction(Object a, Object b) {
			if (a == b)
				return false;
			if (a instanceof Number && b instanceof Number)
				return toBigDecimal(a).doubleValue() != toBigDecimal(b).doubleValue();
			return !a.equals(b);
		}
	}

	Evaluator around() : execution(Evaluator Evaluator.constructInitialEvaluator()) {
		Evaluator eval = proceed();
		eval.env.put(Symbol.create("="), new EqualsImpl());
		return eval;
	}
}
