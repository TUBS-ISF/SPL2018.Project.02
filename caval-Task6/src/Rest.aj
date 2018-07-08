import de.neominik.caval.evaluator.Evaluator;
import de.neominik.caval.lang.AFn;
import de.neominik.caval.lang.Symbol;
import de.neominik.caval.lang.collections.Collection;
import de.neominik.caval.lang.collections.PersistentList;

public aspect Rest {
	public class RestImpl extends AFn {
		@Override
		public Object invoke(Object coll) {
			if (coll == null)
				return PersistentList.of();
			if (coll instanceof String)
				return ((String) coll).substring(1);
			Collection collection = (Collection) coll;
			return collection.rest();
		}
	}

	Evaluator around() : execution(Evaluator Evaluator.constructInitialEvaluator()) {
		Evaluator eval = proceed();
		eval.env.put(Symbol.create("rest"), new RestImpl());
		return eval;
	}
}
