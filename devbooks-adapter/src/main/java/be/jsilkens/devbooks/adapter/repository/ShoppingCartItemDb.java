package be.jsilkens.devbooks.adapter.repository;

import be.jsilkens.devbooks.domain.ShoppingCartItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "SHOPPINGCART_ITEM")
@Entity
public class ShoppingCartItemDb {

    @Id
    @Generated
    @Column(name = "ID")
    private Long id;

    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "BOOK", nullable = false)
    private BookDb book;

    ShoppingCartItemDb(Long id, ShoppingCartItem shoppingCartItem) {
        this.id = id;
        this.quantity = shoppingCartItem.getQuantity();
        this.book = shoppingCartItem.getBook()
                .map(domainBook -> new BookDb(null, domainBook))
                .getOrThrow();
    }
}
