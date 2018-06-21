package de.neominik.caval;

import de.neominik.caval.evaluator.Evaluator;
import de.neominik.caval.lang.Arithmetic;
import de.neominik.caval.lang.Binding;
import de.neominik.caval.lang.CollectionOperations;
import de.neominik.caval.lang.Define;
import de.neominik.caval.lang.Eval;
import de.neominik.caval.lang.Fun;
import de.neominik.caval.lang.IO;
import de.neominik.caval.lang.If;
import de.neominik.caval.lang.Logic;
import de.neominik.caval.lang.Quote;
import de.neominik.caval.lang.Symbol;
import de.neominik.caval.reader.LispReader;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;

import static java.lang.System.in;
import static java.lang.System.out;

@SuppressWarnings("WeakerAccess")
public class Main {
    
    public static Evaluator constructInitialEvaluator() {
        Evaluator eval = original();

        eval.env.put(Symbol.create("count"), new CollectionOperations.Count());
        return eval;
    }
}
