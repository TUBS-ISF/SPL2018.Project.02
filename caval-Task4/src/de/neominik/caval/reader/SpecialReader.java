package de.neominik.caval.reader;

import java.util.Map;

import de.neominik.caval.lang.IFn;

public interface SpecialReader extends IFn {
	Map<Character, IFn> getReadersForChars();
}
