// #condition Quoting
package de.neominik.caval.lang;

public class Quote extends AFn {
    @Override
    public Object invoke(Object evaluator, Object form) {
        return form;
    }
}
