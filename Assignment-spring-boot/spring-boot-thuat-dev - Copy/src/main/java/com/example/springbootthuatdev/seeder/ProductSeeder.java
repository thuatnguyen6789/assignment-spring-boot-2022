package com.example.springbootthuatdev.seeder;

import com.example.springbootthuatdev.entity.Product;
import com.example.springbootthuatdev.entity.enums.ProductStatus;
import com.example.springbootthuatdev.repository.ProductRepository;
import com.example.springbootthuatdev.util.NumberUtil;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j

public class ProductSeeder {
    public static List<Product> products;
    public static final int NUMBER_OF_PRODUCT = 10;

    @Autowired
    ProductRepository productRepository;

    public void generate() {
        log.debug("----------Seeding product----------");
        Faker faker = new Faker();
        products = new ArrayList<>();
        for(int i = 0; i < NUMBER_OF_PRODUCT; i++) {
            products.add(Product.builder()
                    .id(UUID.randomUUID().toString())
                    .name(faker.name().name())
                    .price(new BigDecimal(NumberUtil.getRandomNumber(100, 1000) * 1000))
                    .thumbnails(faker.avatar().image())
                    .detail(faker.lorem().sentence())
                    .status(ProductStatus.ACTIVE)
                    .build());
        }
        productRepository.saveAll(products);
        log.debug("---------End of seeding product-----------");
    }
}
