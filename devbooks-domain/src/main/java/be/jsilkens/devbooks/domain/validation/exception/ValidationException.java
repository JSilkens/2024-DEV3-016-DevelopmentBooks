package be.jsilkens.devbooks.domain.validation.exception;

import be.jsilkens.devbooks.domain.validation.error.FunctionalError;

import java.util.List;
import java.util.stream.Collectors;

public class ValidationException extends RuntimeException {

    public ValidationException(List<FunctionalError> errors) {
        super(errors.stream()
                .map(FunctionalError::print)
                .collect(Collectors.joining(",")));
    }
}
