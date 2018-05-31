package de.neominik.caval.lang.collections;

import de.neominik.caval.lang.AFn;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PersistentVector extends AFn implements Vector {
    private static final PersistentVector emptyVector = new PersistentVector(new Object[0]);
    private final Object[] items;

    private PersistentVector(Object[] items) {
        this.items = items;
    }

    public static PersistentVector of() {
        return emptyVector;
    }

    public static PersistentVector of(Object x) {
        return new PersistentVector(new Object[]{x});
    }

    public static PersistentVector of(Object... xs) {
        return new PersistentVector(Arrays.copyOf(xs, xs.length));
    }

    @Override
    public int count() {
        return items.length;
    }

    @Override
    public PersistentVector conj(Object x) {
        final Object[] newItems = Arrays.copyOf(items, items.length + 1);
        newItems[items.length] = x;
        return new PersistentVector(newItems);
    }

    @Override
    public Object first() {
        return items[0];
    }

    @Override
    public PersistentVector rest() {
        return new PersistentVector(Arrays.copyOfRange(items, 1, items.length));
    }

    @Override
    public int size() {
        return count();
    }

    @Override
    public boolean isEmpty() {
        return count() == 0;
    }

    @Override
    public boolean contains(Object o) {
        return stream().anyMatch(e -> e.hashCode() == o.hashCode());
    }

    @Override
    public Iterator iterator() {
        return Arrays.asList(items).iterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, items.length);
    }

    @Override
    public boolean add(Object o) {
        throw new UnsupportedOperationException("No adding to PersistentVector");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("No removing for PersistentVector");
    }

    @Override
    public boolean addAll(java.util.Collection c) {
        throw new UnsupportedOperationException("No adding to PersistentVector");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("No clearing of PersistentVector");
    }

    @Override
    public boolean retainAll(java.util.Collection c) {
        return stream().anyMatch(c::contains);
    }

    @Override
    public boolean removeAll(java.util.Collection c) {
        throw new UnsupportedOperationException("No removing for PersistentVector");
    }

    @Override
    public boolean containsAll(java.util.Collection c) {
        return c.stream().allMatch(this::contains);
    }

    @Override
    public Object[] toArray(Object[] a) {
        return Arrays.copyOf(items, items.length);
    }

    @Override
    public Stream<Object> stream() {
        return Arrays.stream(items);
    }

    @Override
    public Object invoke(Object index) {
        int i = (Integer) index;
        return items[i];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof PersistentVector) {
            PersistentVector that = (PersistentVector) o;

            return Arrays.equals(items, that.items);
        } else {
            return equals(this, o);
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(items);
    }

    @Override
    public String toString() {
        return '[' + stream().map(Object::toString).collect(Collectors.joining(" ")) + ']';
    }
}
