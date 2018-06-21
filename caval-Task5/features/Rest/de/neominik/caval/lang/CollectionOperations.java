package de.neominik.caval.lang;

import de.neominik.caval.lang.collections.Collection;
import de.neominik.caval.lang.collections.PersistentList;


@SuppressWarnings("unchecked")
public abstract class CollectionOperations {
    public static class Rest extends AFn {
        @Override
        public Object invoke(Object coll) {
            if (coll == null) return PersistentList.of();
            if (coll instanceof String) return ((String) coll).substring(1);
            Collection collection = (Collection) coll;
            return collection.rest();
        }
    }
}
