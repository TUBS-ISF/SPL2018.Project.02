import de.neominik.caval.evaluator.Evaluator;
import de.neominik.caval.lang.CollectionOperations;
import de.neominik.caval.lang.Symbol;

public aspect Conjoin {

	Evaluator around() : execution(Evaluator Evaluator.constructInitialEvaluator()) {
		Evaluator eval = proceed();
		eval.env.put(Symbol.create("conj"), new CollectionOperations.Conjoin());
		return eval;
	}
}
