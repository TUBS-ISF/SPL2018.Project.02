package de.neominik.caval.lang;

import de.neominik.caval.lang.collections.Collection;
import de.neominik.caval.lang.collections.PersistentList;


@SuppressWarnings("unchecked")
public abstract class CollectionOperations {
    public static class Count extends AFn {
        @Override
        public Object invoke(Object coll) {
            if (coll == null) return 0;
            if (coll instanceof String) return ((String) coll).length();
            Collection collection = (Collection) coll;
            return collection.count();
        }
    }
}
