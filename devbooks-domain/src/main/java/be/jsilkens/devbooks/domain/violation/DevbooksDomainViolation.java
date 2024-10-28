package be.jsilkens.devbooks.domain.violation;

import be.jsilkens.devbooks.domain.validation.error.FunctionalError;

public record DevbooksDomainViolation(String message) implements FunctionalError {

    @Override
    public String print() {
        return FunctionalError.super.print();
    }
}
