package com.example.springbootthuatdev.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cartitems")
public class CartItem {
    @Id
    private String id;
    private String name;
    private String thumbnails;
    private int quantity;
    private BigDecimal price;
}
