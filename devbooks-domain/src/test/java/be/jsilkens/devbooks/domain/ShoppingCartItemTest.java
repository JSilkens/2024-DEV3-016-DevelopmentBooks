package be.jsilkens.devbooks.domain;

import be.jsilkens.devbooks.domain.validation.Result;
import be.jsilkens.devbooks.domain.violation.DevbooksDomainViolation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static be.jsilkens.devbooks.domain.Currency.EUR;

class ShoppingCartItemTest {

    @Test
    void givenValidShoppingCartItem_whenValidating_thenSuccess() {
        // Given
        ShoppingCartItem shoppingCartItem = ShoppingCartItem.builder()
                .quantity(2)
                .book(Book.builder()
                        .author("Ernest Cline")
                        .title("Ready Player One")
                        .price(Money.builder().currency(EUR).amount(15.00).build().validate())
                        .build().validate())
                .build();

        Result<ShoppingCartItem> expected = Result.success(shoppingCartItem);

        // When
        Result<ShoppingCartItem> actual = shoppingCartItem.validate();


        //Then
        Assertions.assertThat(actual).isEqualTo(expected);

    }

    @Test
    void givenShoppingCartItemWithNegativeQuantity_whenValidating_thenError() {
        // Given
        ShoppingCartItem shoppingCartItem = ShoppingCartItem.builder()
                .quantity(-2)
                .book(Book.builder()
                        .author("Ernest Cline")
                        .title("Ready Player One")
                        .price(Money.builder().currency(EUR).amount(15.00).build().validate())
                        .build().validate())
                .build();

        Result<ShoppingCartItem> expected = Result.failure(new DevbooksDomainViolation("Quantity must be positive"));

        // When
        Result<ShoppingCartItem> actual = shoppingCartItem.validate();


        //Then
        Assertions.assertThat(actual).isEqualTo(expected);

    }

    @Test
    void givenShoppingCartItemWithInvalidBook_whenValidating_thenError() {
        // Given
        ShoppingCartItem shoppingCartItem = ShoppingCartItem.builder()
                .quantity(2)
                .book(Result.failure(new DevbooksDomainViolation("ERROR")))
                .build();

        Result<ShoppingCartItem> expected = Result.failure(new DevbooksDomainViolation("ERROR"));

        // When
        Result<ShoppingCartItem> actual = shoppingCartItem.validate();


        //Then
        Assertions.assertThat(actual).isEqualTo(expected);

    }

    @Test
    void givenShoppingCartItemWithNoBookProvided_whenValidating_thenError() {
        // Given
        ShoppingCartItem shoppingCartItem = ShoppingCartItem.builder()
                .quantity(2)
                .build();

        Result<ShoppingCartItem> expected = Result.failure(new DevbooksDomainViolation("Book must be provided"));

        // When
        Result<ShoppingCartItem> actual = shoppingCartItem.validate();


        //Then
        Assertions.assertThat(actual).isEqualTo(expected);

    }

}