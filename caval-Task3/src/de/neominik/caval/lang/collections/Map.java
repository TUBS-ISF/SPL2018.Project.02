package de.neominik.caval.lang.collections;

public interface Map extends Collection {
    Object get(Object key);

    Collection keys();

    Collection vals();

    Map assoc(Object key, Object value);

    Map dissoc(Object key);
}
