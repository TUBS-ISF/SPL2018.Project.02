package de.neominik.caval.reader;

import de.neominik.caval.lang.AFn;

import java.io.Reader;

import static de.neominik.caval.reader.LispReader.read1;

public class CommentReader extends AFn {
    @Override
    public Object invoke(Object reader, Object semicolon) {
        Reader r = (Reader) reader;
        int ch;
        do {
            ch = read1(r);
        } while (ch != -1 && ch != '\n' && ch != '\r');
        return r;
    }
}
