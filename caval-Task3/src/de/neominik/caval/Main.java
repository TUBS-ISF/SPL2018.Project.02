package de.neominik.caval;

import de.neominik.caval.evaluator.Evaluator;
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
    private boolean replInput = false;

    @SuppressWarnings("CanBeFinal")
    @Option(name = "-h", aliases = "--help", help = true)
    private boolean printHelp = false;

    private Evaluator eval = Evaluator.constructInitialEvaluator();
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
}
