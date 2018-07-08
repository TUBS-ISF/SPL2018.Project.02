import de.neominik.caval.evaluator.Evaluator;
import de.neominik.caval.lang.AFn;
import de.neominik.caval.lang.Symbol;
import de.neominik.caval.lang.collections.Collection;

public aspect Count {
	public class CountImpl extends AFn {
		@Override
		public Object invoke(Object coll) {
			if (coll == null)
				return 0;
			if (coll instanceof String)
				return ((String) coll).length();
			Collection collection = (Collection) coll;
			return collection.count();
		}
	}

	Evaluator around() : execution(Evaluator Evaluator.constructInitialEvaluator()) {
		Evaluator eval = proceed();
		eval.env.put(Symbol.create("count"), new CountImpl());
		return eval;
	}
}
