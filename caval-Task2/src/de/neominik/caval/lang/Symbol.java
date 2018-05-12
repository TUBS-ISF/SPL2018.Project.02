package de.neominik.caval.lang;

import java.util.Objects;

public class Symbol {
    private final String name;

    private Symbol(String name) {
        this.name = name;
    }

    public static Symbol create(String name) {
        return new Symbol(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol symbol = (Symbol) o;
        return Objects.equals(name, symbol.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Symbol{'" + name + "'}";
    }
}
