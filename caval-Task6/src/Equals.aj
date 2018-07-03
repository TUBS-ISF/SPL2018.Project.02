import de.neominik.caval.evaluator.Evaluator;
import de.neominik.caval.lang.Logic;
import de.neominik.caval.lang.Symbol;

public aspect Equals {

	Evaluator around() : execution(Evaluator Evaluator.constructInitialEvaluator()) {
		Evaluator eval = proceed();
		eval.env.put(Symbol.create("="), new Logic.Equals());
		return eval;
	}
}
