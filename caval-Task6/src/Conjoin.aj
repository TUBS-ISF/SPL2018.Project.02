import de.neominik.caval.evaluator.Evaluator;
import de.neominik.caval.lang.AFn;
import de.neominik.caval.lang.Symbol;
import de.neominik.caval.lang.collections.Collection;
import de.neominik.caval.lang.collections.PersistentList;

public aspect Conjoin {
	public class Conj extends AFn {
		@Override
		public Object invoke() {
			return PersistentList.of();
		}

		@Override
		public Object invoke(Object coll) {
			return coll;
		}

		@Override
		public Object invoke(Object coll, Object x) {
			return ((Collection) coll).conj(x);
		}

		@Override
		public Object invoke(Object coll, Object arg2, Object arg3) {
			return ((Collection) coll).conj(arg2).conj(arg3);
		}

		@Override
		public Object invoke(Object coll, Object arg2, Object arg3, Object arg4) {
			return ((Collection) coll).conj(arg2).conj(arg3).conj(arg4);
		}

		@Override
		public Object invoke(Object coll, Object arg2, Object arg3, Object arg4, Object arg5) {
			return ((Collection) coll).conj(arg2).conj(arg3).conj(arg4).conj(arg5);
		}

		@Override
		public Object invoke(Object coll, Object arg2, Object arg3, Object arg4, Object arg5, Object... args) {
			Collection collection = (Collection) coll;
			collection = collection.conj(arg2).conj(arg3).conj(arg4).conj(arg5);
			for (Object arg : args) {
				collection = collection.conj(arg);
			}
			return collection;
		}
	}

	Evaluator around() : execution(Evaluator Evaluator.constructInitialEvaluator()) {
		Evaluator eval = proceed();
		eval.env.put(Symbol.create("conj"), new Conj());
		return eval;
	}
}
