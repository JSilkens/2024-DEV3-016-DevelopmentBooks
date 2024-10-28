package be.jsilkens.devbooks.domain.validation;

import be.jsilkens.devbooks.domain.validation.error.FunctionalError;
import be.jsilkens.devbooks.domain.validation.exception.TechnicalException;
import be.jsilkens.devbooks.domain.validation.exception.ValidationException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class Result<T> {

    private final T value;
    private final List<FunctionalError> errors;

    public static <T> Result<T> success(T value) {
        if (Objects.isNull(value)) {
            throw new TechnicalException("Null value is not allowed when a result is successful");
        }
        return new Result<>(value, Collections.emptyList());
    }

    public boolean isSuccess() {
        return errors.isEmpty() && nonNull(value);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<FunctionalError> getErrors() {
        return errors;
    }

    public T getOrElse(Supplier<? extends T> other) {
        return nonNull(value) ? value : other.get();
    }

    public T getOrThrow() {
        if (hasErrors()) {
            throw new ValidationException(errors);
        }
        return value;
    }

    public static <T> Result<T> failure(FunctionalError error) {
        if (StringUtils.isBlank(error.message())) {
            throw new TechnicalException("Error cannot be blank");
        }
        return new Result<>(null, Collections.singletonList(error));
    }

    public static <T> Result<T> failure(List<FunctionalError> errors) {
        return new Result<>(null, errors);
    }

    public <U> Result<U> map(Function<? super T, U> function) {
        return nonNull(value) && errors.isEmpty() ? Result.success(function.apply(value)) : Result.failure(errors);
    }

    public <U> Result<U> flatMap(Function<? super T, Result<U>> f) {
        return nonNull(value) && errors.isEmpty() ? f.apply(value) : Result.failure(errors);
    }
}
