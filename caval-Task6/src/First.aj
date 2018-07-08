import de.neominik.caval.evaluator.Evaluator;
import de.neominik.caval.lang.AFn;
import de.neominik.caval.lang.Symbol;
import de.neominik.caval.lang.collections.Collection;

public aspect First {
	public class FirstImpl extends AFn {
		@Override
		public Object invoke(Object coll) {
			if (coll == null)
				return null;
			if (coll instanceof String)
				return ((String) coll).substring(0, 1);
			Collection collection = (Collection) coll;
			return collection.first();
		}
	}

	Evaluator around() : execution(Evaluator Evaluator.constructInitialEvaluator()) {
		Evaluator eval = proceed();
		eval.env.put(Symbol.create("first"), new FirstImpl());
		return eval;
	}
}
