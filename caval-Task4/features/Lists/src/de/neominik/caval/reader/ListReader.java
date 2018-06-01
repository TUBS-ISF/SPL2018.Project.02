package de.neominik.caval.reader;

import java.io.PushbackReader;
import java.util.List;
import java.util.Map;

import de.neominik.caval.lang.AFn;
import de.neominik.caval.lang.IFn;
import de.neominik.caval.lang.collections.PersistentList;
import loader.PluginLoader;

public class ListReader extends AFn implements SpecialReader {
	@Override
	public Object invoke(Object reader, Object leftparen) {
		PushbackReader r = (PushbackReader) reader;
		return PersistentList.of(readDelimited(r, ')').iterator());
	}

	@Override
	public Map<Character, IFn> getReadersForChars() {
		return Map.of('(', new ListReader(), ')', new UnmatchedDelimiterReader());
	}

	private List<Object> readDelimited(PushbackReader r, char delim) {
		return PluginLoader.load(CollectionReader.class).get(0).readDelimited(r, delim);
	}
}
