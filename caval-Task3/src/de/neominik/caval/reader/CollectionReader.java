package de.neominik.caval.reader;

import de.neominik.caval.lang.AFn;
import de.neominik.caval.lang.collections.PersistentList;
import de.neominik.caval.lang.collections.PersistentMap;
import de.neominik.caval.lang.collections.PersistentVector;

import java.io.PushbackReader;
import java.util.LinkedList;
import java.util.List;

import static de.neominik.caval.reader.LispReader.read;

class CollectionReader {

    private static final Object READ_FINISHED = new Object();

    private static List<Object> readDelimited(PushbackReader r, char delim) {
        List<Object> a = new LinkedList<>();
        for (; ; ) {
            Object form = read(r, true, delim, READ_FINISHED);
            if (form == READ_FINISHED) {
                return a;
            }
            a.add(form);
        }
    }

    // #ifdef Lists
    public static class ListReader extends AFn {

        @Override
        public Object invoke(Object reader, Object leftparen) {
            PushbackReader r = (PushbackReader) reader;
            return PersistentList.of(readDelimited(r, ')').iterator());
        }

    }
    // #endif

    // #ifdef Vectors
    public static class VectorReader extends AFn {

        @Override
        public Object invoke(Object reader, Object leftparen) {
            PushbackReader r = (PushbackReader) reader;
            return PersistentVector.of(readDelimited(r, ']').toArray());
        }
    }
    // #endif

    // #ifdef Maps
    public static class MapReader extends AFn {

        @Override
        public Object invoke(Object reader, Object leftparen) {
            PushbackReader r = (PushbackReader) reader;
            List<Object> a = readDelimited(r, '}');
            if ((a.size() & 1) == 1) throw new RuntimeException("Map literal must contain an even number of forms.");
            return PersistentMap.of(a.toArray());
        }
    }
    // #endif
}
