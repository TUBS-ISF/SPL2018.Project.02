package de.neominik.caval.reader;

import de.neominik.caval.lang.AFn;

public class UnmatchedDelimiterReader extends AFn {
    @Override
    public Object invoke(Object reader, Object rightDelimiter) {
        throw new RuntimeException("Unmatched delimiter: " + rightDelimiter);
    }
}
