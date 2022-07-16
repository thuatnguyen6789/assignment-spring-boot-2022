package com.example.springbootthuatdev.entity;

import com.example.springbootthuatdev.entity.base.BaseEntity;
import com.example.springbootthuatdev.entity.enums.ProductStatus;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")

public class Product extends BaseEntity {
    @Id
    private String id;
    private String name;
    private BigDecimal price;
    private String description;
    @Lob
    //@Column(name = "fake_detail", columnDefinition = "text")
    private String detail;
    private String thumbnails;
    @Enumerated(EnumType.ORDINAL)
    private ProductStatus status;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;
}
