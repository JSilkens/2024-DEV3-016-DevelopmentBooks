package be.jsilkens.devbooks.domain.validation;

public interface Validatable<T> {

    Result<T> validate();
}
