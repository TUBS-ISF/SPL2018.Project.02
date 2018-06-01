package de.neominik.caval.reader;

import static de.neominik.caval.reader.LispReader.read;

import java.io.PushbackReader;
import java.util.LinkedList;
import java.util.List;

public class CollectionReaderImpl implements CollectionReader {
    private static final Object READ_FINISHED = new Object();

    @Override
    public List<Object> readDelimited(PushbackReader r, char delim) {
        List<Object> a = new LinkedList<>();
        for (; ; ) {
            Object form = read(r, true, delim, READ_FINISHED);
            if (form == READ_FINISHED) {
                return a;
            }
            a.add(form);
        }
    }
}
