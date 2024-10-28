package be.jsilkens.devbooks.domain.validation;

import be.jsilkens.devbooks.domain.validation.util.TestFunctionalError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ValidatorTest {

    @Test
    void givenValidator_whenValidatePredicateTrueAndNullResult_thenReturnsSameValidator() {
        // Given
        Supplier<Logger> loggerSupplier = () -> mock(Logger.class);
        Validator<String> validator = new Validator<>(loggerSupplier);

        // When
        Validator<String> resultValidator = validator.validate(true, (Result<String>) null);

        // Then
        assertThat(resultValidator).isEqualTo(validator);
        Result<String> result = resultValidator.evaluate(() -> "Success");
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getErrors()).isEmpty();
        assertThat(result.getOrThrow()).isEqualTo("Success");
    }

    @Test
    void givenValidator_whenValidatePredicateFalseWithResultErrors_thenAddErrors() {
        // Given
        Supplier<Logger> loggerSupplier = () -> mock(Logger.class);
        Validator<String> validator = new Validator<>(loggerSupplier);
        TestFunctionalError error1 = new TestFunctionalError("Error 1");
        TestFunctionalError error2 = new TestFunctionalError("Error 2");

        Result<String> errorResult = Result.failure(Arrays.asList(error1, error2));

        // When
        Validator<String> resultValidator = validator.validate(false, errorResult);

        // Then
        assertThat(resultValidator).isNotEqualTo(validator);

        Result<String> result = resultValidator.evaluate(() -> "Success");
        assertFalse(result.isSuccess());

        assertThat(result.getErrors()).containsExactlyInAnyOrder(error1, error2);
    }

    @Test
    void givenValidator_whenMultipleValidations_thenCollectsAllErrors() {
        // Given
        Supplier<Logger> loggerSupplier = () -> mock(Logger.class);
        Validator<String> validator = new Validator<>(loggerSupplier);
        TestFunctionalError error1 = new TestFunctionalError("Error 1");
        TestFunctionalError error2 = new TestFunctionalError("Error 2");

        // When chaining false validations
        Validator<String> resultValidator = validator
                .validate(false, () -> error1)
                .validate(false, () -> error2);

        // Then
        Result<String> result = resultValidator.evaluate(() -> "Success");
        assertFalse(result.isSuccess());

        assertThat(result.getErrors()).containsExactlyInAnyOrder(error1, error2);
    }

    @Test
    void givenValidatorWithExistingErrors_whenValidateAlwaysTrue_thenKeepsExistingErrors() {
        // Given
        Supplier<Logger> loggerSupplier = () -> mock(Logger.class);
        TestFunctionalError initialError = new TestFunctionalError("Initial Error");
        Validator<String> validator = new Validator<>(Collections.singletonList(initialError), loggerSupplier);

        // When validating with a true predicate
        Validator<String> resultValidator = validator.validate(true, () -> new TestFunctionalError("Should not be added"));

        // Then
        assertThat(resultValidator).isEqualTo(validator);

        Result<String> result = resultValidator.evaluate(() -> "Should not succeed");
        assertFalse(result.isSuccess());

        assertThat(result.getErrors()).containsExactly(initialError);
    }
}