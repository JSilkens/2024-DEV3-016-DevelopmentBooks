package be.jsilkens.devbooks.domain;

import be.jsilkens.devbooks.domain.validation.Result;
import be.jsilkens.devbooks.domain.validation.util.TestFunctionalError;
import be.jsilkens.devbooks.domain.violation.DevbooksDomainViolation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BookTest {


    @Test
    void givenCorrectBook_whenValidating_thenSuccessfulResult() {
        // Given
        Book book = Book.builder()
                .title("Count Zero")
                .author("William Gibson")
                .yearPublished(1986)
                .price(Result.success(Money.builder().currency(Currency.EUR).amount(15.00).build()))
                .build();

        Result<Book> expected = Result.success(book);

        // When
        Result<Book> actual = book.validate();

        // Then
        Assertions.assertThat(actual).isEqualTo(expected);

    }

    @Test
    void givenCorrectBookWithNoYearPublished_whenValidating_thenSuccessfulResult() {
        // Given
        Book book = Book.builder()
                .title("Count Zero")
                .author("William Gibson")
                .price(Result.success(Money.builder().currency(Currency.EUR).amount(15.00).build()))
                .build();

        Result<Book> expected = Result.success(book);

        // When
        Result<Book> actual = book.validate();

        // Then
        Assertions.assertThat(actual).isEqualTo(expected);

    }

    @Test
    void givenBookWithBlankTitle_whenValidating_thenValidationError() {
        // Given
        Book book = Book.builder()
                .title("   ")
                .author("William Gibson")
                .yearPublished(1986)
                .price(Result.success(Money.builder().currency(Currency.EUR).amount(15.00).build()))
                .build();

        Result<Book> expected = Result.failure(new DevbooksDomainViolation("Book title cannot be blank"));

        // When
        Result<Book> actual = book.validate();

        // Then
        Assertions.assertThat(actual).isEqualTo(expected);

    }

    @Test
    void givenBookWithBlankAuthor_whenValidating_thenValidationError() {
        // Given
        Book book = Book.builder()
                .title("Count Zero")
                .author("    ")
                .yearPublished(1986)
                .price(Result.success(Money.builder().currency(Currency.EUR).amount(15.00).build()))
                .build();

        Result<Book> expected = Result.failure(new DevbooksDomainViolation("Book author cannot be blank"));

        // When
        Result<Book> actual = book.validate();

        // Then
        Assertions.assertThat(actual).isEqualTo(expected);

    }

    @Test
    void givenBookWithInvalidPrice_whenValidating_thenValidationError() {
        // Given
        Book book = Book.builder()
                .title("Count Zero")
                .author("Willam Gibson")
                .yearPublished(1986)
                .price(Result.failure(new TestFunctionalError("ERROR")))
                .build();

        Result<Book> expected = Result.failure(new TestFunctionalError("ERROR"));

        // When
        Result<Book> actual = book.validate();

        // Then
        Assertions.assertThat(actual).isEqualTo(expected);

    }

    @Test
    void givenBookWithNoPrice_whenValidating_thenValidationError() {
        // Given
        Book book = Book.builder()
                .title("Count Zero")
                .author("Willam Gibson")
                .yearPublished(1986)
                .build();

        Result<Book> expected = Result.failure(new DevbooksDomainViolation("Price must be provided"));

        // When
        Result<Book> actual = book.validate();

        // Then
        Assertions.assertThat(actual).isEqualTo(expected);

    }

}