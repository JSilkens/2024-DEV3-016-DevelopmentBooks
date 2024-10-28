package be.jsilkens.devbooks.domain.validation.util;


import be.jsilkens.devbooks.domain.validation.error.FunctionalError;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
@EqualsAndHashCode
public class TestFunctionalError implements Serializable, FunctionalError {

    private final String message;

    @Override
    public String message() {
        return message;
    }

    @Override
    public String print() {
        return FunctionalError.super.print();
    }
}
