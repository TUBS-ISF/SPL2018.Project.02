package de.neominik.caval.reader;

import de.neominik.caval.lang.AFn;
import de.neominik.caval.lang.IFn;

import java.io.Reader;
import java.util.Map;

import static de.neominik.caval.reader.LispReader.read1;

public class CommentReader extends AFn implements SpecialReader {
	@Override
	public Object invoke(Object reader, Object semicolon) {
		Reader r = (Reader) reader;
		int ch;
		do {
			ch = read1(r);
		} while (ch != -1 && ch != '\n' && ch != '\r');
		return r;
	}

	@Override
	public Map<Character, IFn> getReadersForChars() {
		return Map.of(';', new CommentReader());
	}
}
