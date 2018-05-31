package de.neominik.caval.reader;

import de.neominik.caval.lang.AFn;
import de.neominik.caval.lang.Symbol;
import de.neominik.caval.lang.collections.PersistentList;

import java.io.PushbackReader;

import static de.neominik.caval.reader.LispReader.read;

public class QuoteReader extends AFn {
    @Override
    public Object invoke(Object reader, Object quote) {
        PushbackReader r = (PushbackReader) reader;
        return PersistentList.of(Symbol.create("quote"), read(r, true, null, null));
    }
}
