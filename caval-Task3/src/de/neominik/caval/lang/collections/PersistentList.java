package de.neominik.caval.lang.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PersistentList implements List {
    private static final PersistentList emptyList = new PersistentList();
    private final Object first;
    private final PersistentList rest;
    private final int count;

    private PersistentList() {
        this.first = null;
        this.rest = this;
        count = 0;
    }

    private PersistentList(Object first, PersistentList rest) {
        this.first = first;
        this.rest = rest;
        count = rest.count() + 1;
    }

    public static PersistentList of() {
        return emptyList;
    }

    public static PersistentList of(Object x) {
        return new PersistentList(x, emptyList);
    }

    public static PersistentList of(Object... xs) {
        Iterator<Object> iterator = Arrays.asList(xs).iterator();
        return of(iterator);
    }

    public static PersistentList of(Iterator xs) {
        if (xs.hasNext()) {
            return new PersistentList(xs.next(), of(xs));
        } else {
            return emptyList;
        }
    }

    @Override
    public int count() {
        return count;
    }

    @Override
    public PersistentList conj(Object x) {
        return new PersistentList(x, this);
    }

    @Override
    public Object first() {
        return first;
    }

    @Override
    public Object second() {
        return rest.first;
    }

    @Override
    public PersistentList rest() {
        return rest;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public boolean contains(Object o) {
        return stream().anyMatch(e -> e.hashCode() == o.hashCode());
    }

    @Override
    public Iterator iterator() {
        return new ArrayList<Object>(this).iterator();
    }

    @Override
    public Object[] toArray() {
        return stream().toArray();
    }

    @Override
    public boolean add(Object o) {
        throw new UnsupportedOperationException("No adding to PersistentList");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("No removing for PersistentList");
    }

    @Override
    public boolean addAll(java.util.Collection c) {
        throw new UnsupportedOperationException("No adding to PersistentList");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("No clearing of PersistentList");
    }

    @Override
    public boolean retainAll(java.util.Collection c) {
        return stream().anyMatch(c::contains);
    }

    @Override
    public boolean removeAll(java.util.Collection c) {
        throw new UnsupportedOperationException("No removing for PersistentList");
    }

    @Override
    public boolean containsAll(java.util.Collection c) {
        return c.stream().allMatch(this::contains);
    }

    @Override
    public Object[] toArray(Object[] a) {
        return stream().toArray();
    }

    @Override
    public Stream<Object> stream() {
        Stream.Builder<Object> builder = Stream.builder();
        PersistentList current = this;
        while (current.count > 0) {
            builder.accept(current.first);
            current = current.rest();
        }
        return builder.build();
    }

    @Override
    public boolean equals(Object o) {
        return equals(this, o);
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (rest != null ? rest.hashCode() : 0);
        result = 31 * result + count;
        return result;
    }

    @Override
    public String toString() {
        return '(' + stream().map(Object::toString).collect(Collectors.joining(" ")) + ')';
    }
}
