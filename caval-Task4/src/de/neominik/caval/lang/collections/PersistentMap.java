package de.neominik.caval.lang.collections;

import de.neominik.caval.lang.AFn;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.stream.Stream;

public class PersistentMap extends AFn implements Map {
    private static final PersistentMap emptyMap = new PersistentMap(new LinkedHashMap<>());
    private final LinkedHashMap<Object, Object> items;

    private PersistentMap(LinkedHashMap<Object, Object> items) {
        this.items = items;
    }

    public static PersistentMap of() {
        return emptyMap;
    }

    public static PersistentMap of(Object... xs) {
        LinkedHashMap<Object, Object> newMap = new LinkedHashMap<>();
        for (int i = 0; i < xs.length; i += 2) {
            newMap.put(xs[i], xs[i + 1]);
        }
        return new PersistentMap(newMap);
    }

    public static PersistentMap of(java.util.Map<Object, Object> map) {
        return new PersistentMap(new LinkedHashMap<>(map));
    }

    @Override
    public Object get(Object key) {
        return items.get(key);
    }

    @Override
    public Collection keys() {
        return PersistentVector.of(items.keySet().toArray());
    }

    @Override
    public Collection vals() {
        return PersistentVector.of(items.values().toArray());
    }

    @Override
    public Map assoc(Object key, Object value) {
        LinkedHashMap<Object, Object> newItems = new LinkedHashMap<>(items);
        newItems.put(key, value);
        return new PersistentMap(newItems);
    }

    @Override
    public Map dissoc(Object key) {
        LinkedHashMap<Object, Object> newItems = new LinkedHashMap<>(items);
        newItems.remove(key);
        return new PersistentMap(newItems);
    }

    @Override
    public int count() {
        return items.size();
    }

    @Override
    public Map conj(Object x) {
        Collection entry = (Collection) x;
        return assoc(entry.first(), entry.rest().first());
    }

    @Override
    public Object first() {
        Object key = items.keySet().iterator().next();
        return PersistentVector.of(key, items.get(key));
    }

    @Override
    public Collection rest() {
        return dissoc(first());
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
        if (!(o instanceof Collection)) return false;
        Collection coll = (Collection) o;
        return items.containsKey(coll.first()) && items.get(coll.first()).hashCode() == coll.rest().first().hashCode();
    }

    @Override
    public Iterator iterator() {
        return items.entrySet().iterator();
    }

    @Override
    public Object[] toArray() {
        return stream().toArray();
    }

    @Override
    public boolean add(Object o) {
        throw new UnsupportedOperationException("No adding to PersistentMap");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("No removing for PersistentMap");
    }

    @Override
    public boolean addAll(java.util.Collection c) {
        throw new UnsupportedOperationException("No adding to PersistentMap");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("No clearing of PersistentMap");
    }

    @Override
    public boolean retainAll(java.util.Collection c) {
        return stream().anyMatch(c::contains);
    }

    @Override
    public boolean removeAll(java.util.Collection c) {
        throw new UnsupportedOperationException("No removing for PersistentMap");
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
    @SuppressWarnings("unchecked")
    public Stream<Object> stream() {
        return (Stream<Object>) (Stream<?>) items.entrySet().stream().map(e -> PersistentVector.of(e.getKey(), e.getValue()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof PersistentMap) {
            PersistentMap that = (PersistentMap) o;

            return items.equals(that.items);
        } else {
            return super.equals(o);
        }
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }
    
    @Override
    public String toString() {
    	return items.toString();
    }
}
