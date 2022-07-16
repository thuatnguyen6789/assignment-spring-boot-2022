package com.example.springbootthuatdev.entity;

import com.example.springbootthuatdev.entity.base.BaseEntity;
import com.example.springbootthuatdev.entity.enums.OrderSimpleStatus;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "orders")

public class Order extends BaseEntity {
    @Id
    private String id;
    private int userId; //0
    private BigDecimal totalPrice;
    @Enumerated(EnumType.ORDINAL)
    private OrderSimpleStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderDetail> orderDetails;

    public void calculateTotalPrice() {
        this.totalPrice = new BigDecimal(0);
        if(this.orderDetails != null && this.orderDetails.size() > 0) {
            for (OrderDetail orderDetail :
                    orderDetails) {
                BigDecimal orderDetailTotalPrice = orderDetail.getUnitPrice().multiply(new BigDecimal(orderDetail.getQuantity()));
                this.totalPrice = this.totalPrice.add(orderDetailTotalPrice);
            }
        }
    }
}
