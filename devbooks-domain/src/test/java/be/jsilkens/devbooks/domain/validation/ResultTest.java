package be.jsilkens.devbooks.domain.validation;

import be.jsilkens.devbooks.domain.validation.error.FunctionalError;
import be.jsilkens.devbooks.domain.validation.exception.TechnicalException;
import be.jsilkens.devbooks.domain.validation.exception.ValidationException;
import be.jsilkens.devbooks.domain.validation.util.TestFunctionalError;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ResultTest {

    @Test
    void givenValue_whenCreatingSuccessfulResult_thenNoErrorsAndValueIsPresent() {
        // Given
        String expected = "Test";

        // When
        Result<String> result = Result.success(expected);

        // Then
        assertThat(result.hasErrors()).isFalse();
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getOrThrow()).isEqualTo(expected);

    }

    @Test
    void givenErrorMessage_whenCreatingFailedResult_thenErrorsAndNoValueIsPresent() {
        // Given
        FunctionalError error = new TestFunctionalError("Error");

        // When
        Result<Object> result = Result.failure(error);

        // Then
        assertThat(result.hasErrors()).isTrue();
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getErrors()).isEqualTo(Collections.singletonList(error));
        assertThrows(ValidationException.class, result::getOrThrow);

    }

    @Test
    void whenCreatingSuccessResultWithNoValue_thenTechnicalExceptionIsThrown() {
        // Given nothing

        // When and Then
        assertThrows(TechnicalException.class, () -> Result.success(null));
    }

    @Test
    void givenSuccessResult_whenMappingResultWithFunction_thenTransformedValueIsReturned() {
        // Given
        Result<Integer> result = Result.success(5);

        // When
        Result<String> mappedResult = result.map(Object::toString);

        // Then
        assertFalse(mappedResult.hasErrors());
        assertEquals("5", mappedResult.getOrThrow());
    }

    @Test
    void givenSuccessResult_whenFlatMappingResultWithFunctionProvidingNewResult_thenNewResultIsReturned() {
        // Given
        Result<Integer> result = Result.success(50);

        // When
        Result<String> flatMappedResult = result.flatMap(num -> Result.success("Value: " + num));

        // Then
        assertFalse(flatMappedResult.hasErrors());
        assertEquals("Value: 50", flatMappedResult.getOrThrow());
    }


    @Test
    void givenFailedResult_whenGetOrElseWithSupplier_thenReturnsDefaultFromSupplier() {
        // Given
        FunctionalError error = new TestFunctionalError("Error");
        Result<Integer> result = Result.failure(error);

        // When
        Integer value = result.getOrElse(() -> 42);

        // Then
        assertEquals(42, value);
    }

    @Test
    void givenMultipleErrors_whenCreatingFailureWithMultipleErrors_thenErrorsArePresentAndNoValue() {
        // Given
        List<FunctionalError> errors = Arrays.asList(new TestFunctionalError("Error1"), new TestFunctionalError("Error2"));

        // When
        Result<String> result = Result.failure(errors);

        // Then
        assertTrue(result.hasErrors());
        assertEquals(2, result.getErrors().size());
    }

    @Test
    void givenBlankError_whenCreatingFailedResult_thenThrowException() {

        assertThrows(TechnicalException.class, () -> Result.failure(new TestFunctionalError("  ")));
    }


}