package be.jsilkens.devbooks.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class ShoppingCart {

    private final List<ShoppingCartItem> items;

}
