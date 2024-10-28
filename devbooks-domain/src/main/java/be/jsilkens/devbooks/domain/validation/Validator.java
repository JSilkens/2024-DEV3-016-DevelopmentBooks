package be.jsilkens.devbooks.domain.validation;

import be.jsilkens.devbooks.domain.validation.error.FunctionalError;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static java.util.Objects.isNull;

@EqualsAndHashCode
public class Validator<T> {

    private final List<FunctionalError> errors;

    private final Supplier<Logger> logger;

    public Validator(List<FunctionalError> errors, Supplier<Logger> supplier) {
        this.errors = errors;
        this.logger = supplier;
    }

    public Validator(Supplier<Logger> logger) {
        this.errors = Collections.emptyList();
        this.logger = logger;
    }

    public Validator<T> validate(boolean predicate, Supplier<FunctionalError> exceptionSupplier) {
        return predicate ? new Validator<>(errors, logger) : new Validator<>(CollectionUtil.join(exceptionSupplier.get(), errors), logger);
    }

    public <U> Validator<T> validate(boolean predicate, Result<U> result) {
        return predicate || isNull(result) ? new Validator<>(errors, logger) : new Validator<>(CollectionUtil.join(result.getErrors(), errors), logger);
    }

    public Result<T> evaluate(Supplier<T> onSuccess) {
        return errors.isEmpty() ? Result.success(onSuccess.get()) : handleError();
    }

    private Result<T> handleError() {
        errors.forEach(e -> logger.get().error(e.print()));
        return Result.failure(errors);
    }


}
