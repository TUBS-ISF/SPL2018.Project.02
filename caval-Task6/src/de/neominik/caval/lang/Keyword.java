package de.neominik.caval.lang;

import java.util.Objects;

public class Keyword {
    private final Symbol symbol;

    private Keyword(Symbol symbol) {
        this.symbol = symbol;
    }

    public static Keyword create(Symbol symbol) {
        return new Keyword(symbol);
    }

    public String getName() {
        return symbol.getName();
    }

    public Symbol getSymbol() {
        return symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Keyword keyword = (Keyword) o;
        return Objects.equals(symbol, keyword.symbol);
    }

    @Override
    public int hashCode() {

        return Objects.hash(symbol);
    }

    @Override
    public String toString() {
        return ":" + symbol.getName();
    }
}
