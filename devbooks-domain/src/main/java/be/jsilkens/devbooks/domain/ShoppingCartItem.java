package be.jsilkens.devbooks.domain;

import be.jsilkens.devbooks.domain.validation.Result;
import be.jsilkens.devbooks.domain.validation.Validatable;
import be.jsilkens.devbooks.domain.validation.Validator;
import be.jsilkens.devbooks.domain.violation.DevbooksDomainViolation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.nonNull;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class ShoppingCartItem implements Validatable<ShoppingCartItem> {

    private final Integer quantity;
    private final Result<Book> book;

    @Override
    public Result<ShoppingCartItem> validate() {
        return new Validator<ShoppingCartItem>(() -> log)
                .validate(quantity > 0, () -> new DevbooksDomainViolation("Quantity must be positive"))
                .validate(nonNull(book), () -> new DevbooksDomainViolation("Book must be provided"))
                .validate(validateBook(), book)
                .evaluate(() -> this);
    }

    private boolean validateBook() {
        return nonNull(book) && book.isSuccess();
    }
}
