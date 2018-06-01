package de.neominik.caval.reader;

import java.io.PushbackReader;
import java.util.List;

public interface CollectionReader {
	List<Object> readDelimited(PushbackReader r, char delim);
}
