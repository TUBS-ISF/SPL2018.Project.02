package de.neominik.caval.reader;

import java.io.PushbackReader;
import java.util.List;
import java.util.Map;

import de.neominik.caval.lang.AFn;
import de.neominik.caval.lang.IFn;
import de.neominik.caval.lang.collections.PersistentVector;
import loader.PluginLoader;

public class VectorReader extends AFn implements SpecialReader {
	@Override
	public Object invoke(Object reader, Object leftparen) {
		PushbackReader r = (PushbackReader) reader;
		return PersistentVector.of(readDelimited(r, ']').toArray());
	}

	@Override
	public Map<Character, IFn> getReadersForChars() {
		return Map.of('[', new VectorReader(), ']', new UnmatchedDelimiterReader());
	}

	private List<Object> readDelimited(PushbackReader r, char delim) {
		return PluginLoader.load(CollectionReader.class).get(0).readDelimited(r, delim);
	}
}
