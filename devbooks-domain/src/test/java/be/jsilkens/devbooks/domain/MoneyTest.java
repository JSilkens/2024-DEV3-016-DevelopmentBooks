package be.jsilkens.devbooks.domain;

import be.jsilkens.devbooks.domain.validation.Result;
import be.jsilkens.devbooks.domain.violation.DevbooksDomainViolation;
import org.junit.jupiter.api.Test;

import static be.jsilkens.devbooks.domain.Currency.EUR;
import static org.assertj.core.api.Assertions.assertThat;

class MoneyTest {


    @Test
    void givenCorrectMoney_whenValidating_thenSuccessful() {
        // Given
        Money money = Money.builder()
                .currency(EUR)
                .amount(15.00)
                .build();
        Result<Money> expected = Result.success(money);

        // When
        Result<Money> actual = money.validate();

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenCorrectMoney_whenFormatting_thenAmountFormatted() {
        // Given
        Money money = Money.builder()
                .currency(EUR)
                .amount(15.568741)
                .build();

        String expected = "15.57";

        // When
        String actual = money.getFormattedAmount();

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenMoneyWithNoCurrency_whenValidating_thenFailed() {
        // Given
        Money money = Money.builder()
                .amount(15.00)
                .build();

        Result<Money> expected = Result.failure(new DevbooksDomainViolation("Currency must be provided"));

        // When
        Result<Money> actual = money.validate();

        //Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenMoneyWithNegativeAmount_whenValidating_thenFailed() {
        // Given
        Money money = Money.builder()
                .currency(EUR)
                .amount(-15.00)
                .build();

        Result<Money> expected = Result.failure(new DevbooksDomainViolation("Money value cannot be negative"));

        // When
        Result<Money> actual = money.validate();

        //Then
        assertThat(actual).isEqualTo(expected);
    }
}