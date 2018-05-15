package de.neominik.caval.lang.collections;

import java.util.stream.Stream;

public interface Collection extends java.util.Collection {
    int count();

    Collection conj(Object x);

    Object first();

    Collection rest();

    Stream<Object> stream();

    default boolean equals(Collection a, Object b) {
        if (a == b) return true;
        if (b == null || !Collection.class.isAssignableFrom(b.getClass())) return false;

        Collection that = (Collection) b;
        if (a.count() != that.count()) return false;
        if (a.count() == 0) return true;
        return a.first().equals(that.first()) && a.rest().equals(that.rest());
    }
}
