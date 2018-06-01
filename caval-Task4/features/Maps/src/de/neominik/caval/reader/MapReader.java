package de.neominik.caval.reader;

import java.io.PushbackReader;
import java.util.List;
import java.util.Map;

import de.neominik.caval.lang.AFn;
import de.neominik.caval.lang.IFn;
import de.neominik.caval.lang.collections.PersistentMap;
import loader.PluginLoader;

public class MapReader extends AFn implements SpecialReader {
	@Override
	public Object invoke(Object reader, Object leftparen) {
		PushbackReader r = (PushbackReader) reader;
		List<Object> a = readDelimited(r, '}');
		if ((a.size() & 1) == 1)
			throw new RuntimeException("Map literal must contain an even number of forms.");
		return PersistentMap.of(a.toArray());
	}

	@Override
	public Map<Character, IFn> getReadersForChars() {
		return Map.of('{', new MapReader(), '}', new UnmatchedDelimiterReader());
	}

	private List<Object> readDelimited(PushbackReader r, char delim) {
		return PluginLoader.load(CollectionReader.class).get(0).readDelimited(r, delim);
	}
}
