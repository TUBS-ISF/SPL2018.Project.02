package de.neominik.caval.reader;

import de.neominik.caval.lang.AFn;
import de.neominik.caval.lang.IFn;
import de.neominik.caval.lang.Symbol;
import de.neominik.caval.lang.collections.PersistentList;

import java.io.PushbackReader;
import java.util.Map;

import static de.neominik.caval.reader.LispReader.read;

public class QuoteReader extends AFn implements SpecialReader {
	@Override
	public Object invoke(Object reader, Object quote) {
		PushbackReader r = (PushbackReader) reader;
		return PersistentList.of(Symbol.create("quote"), read(r, true, null, null));
	}

	@Override
	public Map<Character, IFn> getReadersForChars() {
		return Map.of('\'', new QuoteReader());
	}
}
