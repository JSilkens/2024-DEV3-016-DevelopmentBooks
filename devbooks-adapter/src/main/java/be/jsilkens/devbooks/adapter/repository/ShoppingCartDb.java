package be.jsilkens.devbooks.adapter.repository;

import be.jsilkens.devbooks.domain.ShoppingCartItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "SHOPPINGCART")
@Entity
public class ShoppingCartDb {

    @Id
    @Column(name = "UUID", nullable = false, unique = true, updatable = false)
    private UUID uuid;

    @OneToMany(mappedBy = "shoppingcart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ShoppingCartItemDb> shoppingCartItems;


    ShoppingCartDb(UUID uuid, Set<ShoppingCartItem> shoppingCartItems) {
        this.uuid = uuid;
        this.shoppingCartItems = shoppingCartItems.stream()
                .map(shoppingCartItem ->
                        new ShoppingCartItemDb(null, shoppingCartItem)
                ).collect(Collectors.toSet());
    }
}
