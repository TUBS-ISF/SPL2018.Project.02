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

    @SuppressWarnings("unused")
    @Option(name = "-f", aliases = "--file", forbids = "-r", usage = "interpret source file", metaVar = "<file>")
    private File fileInput;

    @SuppressWarnings("CanBeFinal")
    @Option(name = "-r", aliases = "--repl", forbids = "-f", usage = "start a repl")
    private boolean replInput = true;

    @SuppressWarnings("CanBeFinal")
    @Option(name = "-h", aliases = "--help", help = true)
    private boolean printHelp = false;

    private Evaluator eval = constructInitialEvaluator();
    private LispReader reader = new LispReader();

    public static void main(String[] args) {
        Main main = handleCmdLineArgs(args);
        main.startInterpreter();
    }

    private static Main handleCmdLineArgs(String[] args) {
        final Main main = new Main();
        final CmdLineParser parser = new CmdLineParser(main);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            out.println(e.getLocalizedMessage());
            printUsage(parser);
            System.exit(1);
        }
        if (main.printHelp || !main.replInput && main.fileInput == null) {
            printUsage(parser);
            System.exit(0);
        }
        return main;
    }

    private static void printUsage(CmdLineParser parser) {
        out.println("Usage:");
        parser.printUsage(out);
    }

    private void startInterpreter() {
        if (replInput) {
            startRepl();
        } else {
            readFromFile();
        }
    }

    private void startRepl() {
        interpret(new InputStreamReader(in));
    }

    private void readFromFile() {
        try {
            interpret(new FileReader(fileInput));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void interpret(Reader in) {
        PushbackReader r = new PushbackReader(in);
        Object eofRead = new Object();
        for (; ; ) {
            printPrompt();
            try {
                Object form = reader.read(r, eofRead);
                if (form == eofRead) form = reader.read("(exit)");
                Object result = eval.evaluate(form);
                if (replInput) out.println(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void printPrompt() {
        if (replInput) out.print("=> ");
    }
    
    public static Evaluator constructInitialEvaluator() {
        Evaluator eval = new Evaluator(null);
        eval.env.put(Symbol.create("def"), new Define(eval.env));
        eval.env.put(Symbol.create("let"), new Binding());
        eval.env.put(Symbol.create("fn"), new Fun());
        eval.env.put(Symbol.create("if"), new If());
        eval.env.put(Symbol.create("quote"), new Quote());
        eval.env.put(Symbol.create("eval"), new Eval());
        eval.specialForms.addAll(eval.env.keySet());

        eval.env.put(Symbol.create("+"), new Arithmetic.Plus());
        eval.env.put(Symbol.create("-"), new Arithmetic.Minus());
        eval.env.put(Symbol.create("*"), new Arithmetic.Times());
        eval.env.put(Symbol.create("/"), new Arithmetic.Divide());
        eval.env.put(Symbol.create("println"), new IO.Println());
        eval.env.put(Symbol.create("print"), new IO.Print());
        eval.env.put(Symbol.create("exit"), new IO.Exit());
        return eval;
    }
}
