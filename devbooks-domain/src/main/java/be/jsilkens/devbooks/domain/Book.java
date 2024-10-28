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

import java.util.Objects;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class Book implements Validatable<Book> {
    private final String title;
    private final String author;
    private final Integer yearPublished;
    private final Result<Money> price;

    @Override
    public Result<Book> validate() {
        return new Validator<Book>(() -> log)
                .validate(isNotBlank(title), () -> new DevbooksDomainViolation("Book title cannot be blank"))
                .validate(isNotBlank(author), () -> new DevbooksDomainViolation("Book author cannot be blank"))
                .validate(nonNull(price), () -> new DevbooksDomainViolation("Price must be provided"))
                .validate(validatePrice(), price)
                .evaluate(() -> this);
    }

    private Boolean validatePrice() {
        if (Objects.isNull(price)) {
            return true;
        }
        return price.isSuccess();
    }


}
