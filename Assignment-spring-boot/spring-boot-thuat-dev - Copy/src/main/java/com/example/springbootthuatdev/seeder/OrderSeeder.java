package com.example.springbootthuatdev.seeder;

import com.example.springbootthuatdev.entity.*;
import com.example.springbootthuatdev.entity.enums.OrderSimpleStatus;
import com.example.springbootthuatdev.repository.OrderRepository;
import com.example.springbootthuatdev.util.DateTimeHelper;
import com.example.springbootthuatdev.util.NumberUtil;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

@Component
@Slf4j
public class OrderSeeder {
    public static List<Order> orders;
    public static final int NUMBER_OF_ORDER = 1000;
    public static final int NUMBER_OF_DONE = 600;
    public static final int NUMBER_OF_CANCEL = 200;
    public static final int NUMBER_OF_PENDING = 200;

    public static final int MIN_ORDER_DETAIL = 4;
    public static final int MAX_ORDER_DETAIL = 10;
    public static final int MIN_PRODUCT_QUANTITY = 2;
    public static final int MAX_PRODUCT_QUANTITY = 10;

    @Autowired
    OrderRepository orderRepository;
    List<OrderSeedByTime> seeder;

    public void configData() {
        seeder = new ArrayList<>();
        seeder.add(
                OrderSeedByTime.builder()
                        .orderStatus(OrderSimpleStatus.DONE).seedTypeByTime(OrderSeedByTimeType.DAY).day(18).month(Month.JUNE).year(2022).orderCount(350)
                        .build());
        seeder.add(
                OrderSeedByTime.builder()
                        .orderStatus(OrderSimpleStatus.DONE).seedTypeByTime(OrderSeedByTimeType.DAY).day(17).month(Month.JUNE).year(2022).orderCount(300)
                        .build());
        seeder.add(
                OrderSeedByTime.builder()
                        .orderStatus(OrderSimpleStatus.DONE).seedTypeByTime(OrderSeedByTimeType.MONTH).month(Month.JUNE).year(2022).orderCount(250)
                        .build());
        seeder.add(
                OrderSeedByTime.builder()
                        .orderStatus(OrderSimpleStatus.PROCESSING).seedTypeByTime(OrderSeedByTimeType.MONTH).month(Month.JUNE).year(2022).orderCount(10)
                        .build());
        seeder.add(
                OrderSeedByTime.builder()
                        .orderStatus(OrderSimpleStatus.DONE).seedTypeByTime(OrderSeedByTimeType.MONTH).day(17).month(Month.MAY).year(2022).orderCount(40)
                        .build());
        seeder.add(
                OrderSeedByTime.builder()
                        .orderStatus(OrderSimpleStatus.DONE).seedTypeByTime(OrderSeedByTimeType.MONTH).month(Month.APRIL).year(2022).orderCount(50)
                        .build());
        seeder.add(
                OrderSeedByTime.builder()
                        .orderStatus(OrderSimpleStatus.PROCESSING).seedTypeByTime(OrderSeedByTimeType.MONTH).month(Month.APRIL).year(2022).orderCount(4)
                        .build());
        seeder.add(
                OrderSeedByTime.builder()
                        .orderStatus(OrderSimpleStatus.DONE).seedTypeByTime(OrderSeedByTimeType.MONTH).month(Month.MARCH).year(2022).orderCount(102)
                        .build());
    }
    public void generate() {
        configData();
        log.debug("------------Seeding order-------------");
        Faker faker = new Faker();
        orders = new ArrayList<>();
        for (OrderSeedByTime orderSeedByTime :
                seeder) {
            int numberOfOrder = orderSeedByTime.getOrderCount();
            for (int i = 0; i < numberOfOrder; i++) {
                // l???y random user.
                int randomUserIndex = NumberUtil.getRandomNumber(0, UserSeeder.users.size() - 1);
                User user = UserSeeder.users.get(randomUserIndex);
                // T???o m???i ????n h??ng.
                Order order = new Order();
                order.setId(UUID.randomUUID().toString());
                order.setStatus(orderSeedByTime.getOrderStatus());
                LocalDateTime orderCreatedTime = calculateOrderCreatedTime(orderSeedByTime);
                order.setCreatedAt(orderCreatedTime);
                order.setUpdatedAt(orderCreatedTime);
                order.setUserId(user.getId());
                // T???o danh s??ch order detail cho ????n h??ng.
                Set<OrderDetail> orderDetails = new HashSet<>();
                // map n??y d??ng ????? check s??? t???n t???i c???a s???n ph???m trong order detail.
                HashMap<String, Product> mapProduct = new HashMap<>();
                // generate s??? l?????ng c???a order detail.
                int orderDetailNumber = NumberUtil.getRandomNumber(MIN_ORDER_DETAIL, MAX_ORDER_DETAIL);
                for (int j = 0; j < orderDetailNumber; j++) {
                    // l???y random product.
                    int randomProductIndex = NumberUtil.getRandomNumber(0, ProductSeeder.products.size() - 1);
                    Product product = ProductSeeder.products.get(randomProductIndex);
                    // check t???n t???i
                    if (mapProduct.containsKey(product.getId())) {
                        continue; // b??? qua ho???c c???ng d???n
                    }
                    // t???o order detail theo s???n ph???m random
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setId(new OrderDetailId(order.getId(), product.getId().toString()));
                    orderDetail.setOrder(order); // set quan h???
                    orderDetail.setProduct(product); // set quan h???
                    orderDetail.setUnitPrice(product.getPrice()); // gi?? theo s???n ph???m
                    // random s??? l?????ng theo c???u h??nh
                    orderDetail.setQuantity(NumberUtil.getRandomNumber(MIN_PRODUCT_QUANTITY, MAX_PRODUCT_QUANTITY));
                    // ????a v??o danh s??ch order detail
                    orderDetails.add(orderDetail);
                    mapProduct.put(product.getId().toString(), product);
                }
                // set quan h??? v???i order
                order.setOrderDetails(orderDetails);
                order.calculateTotalPrice();
                orders.add(order);
            }
        }
        orderRepository.saveAll(orders);
        log.debug("--------------End of seeding order-------------");
    }

    private LocalDateTime calculateOrderCreatedTime(OrderSeedByTime orderSeedByTime) {
        LocalDateTime result = null;
        LocalDateTime tempLocalDateTime = null;
        int tempMonth = 1;
        int tempYear = 2022;
        switch (orderSeedByTime.getSeedTypeByTime()) {
            case YEAR:
                // n???u theo n??m th?? random th??ng v?? ng??y.
                tempMonth = DateTimeHelper.getRandomMonth().getValue();
                tempYear = orderSeedByTime.getYear();
                tempLocalDateTime = LocalDateTime.of(tempYear, tempMonth, 1, 0, 0, 0);
                result = tempLocalDateTime.plusMonths(1).minusDays(1);
                break;
            case MONTH:
                // n???u theo th??ng, n??m th?? random ng??y.
                tempMonth = orderSeedByTime.getMonth().getValue();
                tempYear = orderSeedByTime.getYear();
                tempLocalDateTime = LocalDateTime.of(tempYear, tempMonth, 1, 0, 0, 0);
                LocalDateTime lastDayOfMonth = tempLocalDateTime.plusMonths(1).minusDays(1);
                int randomDay = NumberUtil.getRandomNumber(1, lastDayOfMonth.getDayOfMonth());
                result = LocalDateTime.of(tempYear, tempMonth, randomDay, 0, 0, 0);
                if (result.isAfter(LocalDateTime.now())) {
                    // n???u sau th???i gian hi???n t???i, t???c l?? th??ng n??m ??ang th???i gian hi???n t???i
                    randomDay = NumberUtil.getRandomNumber(1, LocalDateTime.now().getDayOfMonth());
                    result = LocalDateTime.of(tempYear, tempMonth, randomDay, 0, 0, 0);
                }
                break;
            case DAY:
                // n???u l?? ng??y th?? fix
                result = LocalDateTime.of(orderSeedByTime.getYear(), orderSeedByTime.getMonth(), orderSeedByTime.getDay(), 0, 0, 0);
                break;
        }
        return result;
    }
}
