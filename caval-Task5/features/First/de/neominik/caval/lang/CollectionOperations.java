package de.neominik.caval.lang;

import de.neominik.caval.lang.collections.Collection;
import de.neominik.caval.lang.collections.PersistentList;


@SuppressWarnings("unchecked")
public abstract class CollectionOperations {
    public static class First extends AFn {
        @Override
        public Object invoke(Object coll) {
            if (coll == null) return null;
            if (coll instanceof String) return ((String) coll).substring(0, 1);
            Collection collection = (Collection) coll;
            return collection.first();
        }
    }
}
