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

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class Money implements Validatable<Money> {
    private final Currency currency;
    private final Double amount;

    @Override
    public Result<Money> validate() {
        return new Validator<Money>(() -> log)
                .validate(amount > 0, () -> new DevbooksDomainViolation("Money value cannot be negative"))
                .validate(Objects.nonNull(currency), () -> new DevbooksDomainViolation("Currency must be provided"))
                .evaluate(() -> this);
    }

    public String getFormattedAmount() {
        return String.format("%.2f", amount);
    }
}
