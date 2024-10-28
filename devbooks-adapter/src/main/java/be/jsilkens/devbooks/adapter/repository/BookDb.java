package be.jsilkens.devbooks.adapter.repository;

import be.jsilkens.devbooks.domain.Book;
import be.jsilkens.devbooks.domain.Currency;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "BOOK")
@Entity
public class BookDb {

    @Id
    @Column(name = "UUID", nullable = false, unique = true, updatable = false)
    private UUID uuid;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "AUTHOR", nullable = false)
    private String author;

    @Column(name = "YEAR_PUBLISHED", nullable = false)
    private Integer yearPublished;

    @Column(name = "PRICE", nullable = false)
    private Double price;

    @Column(name = "CURRENCY", nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    BookDb(UUID uuid, Book book) {
        this.uuid = uuid;
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.yearPublished = book.getYearPublished();
        this.price = book.getPrice().getOrThrow().getAmount();
        this.currency = book.getPrice().getOrThrow().getCurrency();
    }

}
