package com.epam.training.money.product;

import com.epam.training.money.Money;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");

    private final List<Product> productList = List.of(
        Product.builder()
            .withName("Pentium3")
            .withNetPrice(new Money(500, HUF_CURRENCY))
            .build(),
        Product.builder()
            .withName("Pentium4")
            .withNetPrice(new Money(5000, HUF_CURRENCY))
            .build(),
        Product.builder()
            .withName("Pentium5")
            .withNetPrice(new Money(50000, HUF_CURRENCY))
            .build()
    );

    @Override
    public List<Product> getProductList() {
        return productList;
    }

    @Override
    public Optional<Product> getProductByName(String productName) {
        return productList.stream()
            .filter(product -> product.getName().equals(productName))
            .findFirst();
    }
}
