package be.jsilkens.devbooks.domain.validation.error;

public interface FunctionalError {

    String message();

    default String print() {
        return String.format("%s : Message: %s", this.getClass().getName(), this.message());
    }
}
