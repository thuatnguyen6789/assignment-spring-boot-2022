package com.example.springbootthuatdev.api;

import com.example.springbootthuatdev.entity.Order;
import com.example.springbootthuatdev.entity.search.FilterParameter;
import com.example.springbootthuatdev.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/orders")

public class OrderApi {
    final OrderService orderService;

    public OrderApi(OrderService orderService) {
        this.orderService = orderService;
    }
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> findAllByOneObject(
            @RequestBody FilterParameter param) {
        Page<Order> result = this.orderService.findAll(param);
        return ResponseEntity.ok().body(result);
    }
}
