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
                // lấy random user.
                int randomUserIndex = NumberUtil.getRandomNumber(0, UserSeeder.users.size() - 1);
                User user = UserSeeder.users.get(randomUserIndex);
                // Tạo mới đơn hàng.
                Order order = new Order();
                order.setId(UUID.randomUUID().toString());
                order.setStatus(orderSeedByTime.getOrderStatus());
                LocalDateTime orderCreatedTime = calculateOrderCreatedTime(orderSeedByTime);
                order.setCreatedAt(orderCreatedTime);
                order.setUpdatedAt(orderCreatedTime);
                order.setUserId(user.getId());
                // Tạo danh sách order detail cho đơn hàng.
                Set<OrderDetail> orderDetails = new HashSet<>();
                // map này dùng để check sự tồn tại của sản phẩm trong order detail.
                HashMap<String, Product> mapProduct = new HashMap<>();
                // generate số lượng của order detail.
                int orderDetailNumber = NumberUtil.getRandomNumber(MIN_ORDER_DETAIL, MAX_ORDER_DETAIL);
                for (int j = 0; j < orderDetailNumber; j++) {
                    // lấy random product.
                    int randomProductIndex = NumberUtil.getRandomNumber(0, ProductSeeder.products.size() - 1);
                    Product product = ProductSeeder.products.get(randomProductIndex);
                    // check tồn tại
                    if (mapProduct.containsKey(product.getId())) {
                        continue; // bỏ qua hoặc cộng dồn
                    }
                    // tạo order detail theo sản phẩm random
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setId(new OrderDetailId(order.getId(), product.getId().toString()));
                    orderDetail.setOrder(order); // set quan hệ
                    orderDetail.setProduct(product); // set quan hệ
                    orderDetail.setUnitPrice(product.getPrice()); // giá theo sản phẩm
                    // random số lượng theo cấu hình
                    orderDetail.setQuantity(NumberUtil.getRandomNumber(MIN_PRODUCT_QUANTITY, MAX_PRODUCT_QUANTITY));
                    // đưa vào danh sách order detail
                    orderDetails.add(orderDetail);
                    mapProduct.put(product.getId().toString(), product);
                }
                // set quan hệ với order
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
                // nếu theo năm thì random tháng và ngày.
                tempMonth = DateTimeHelper.getRandomMonth().getValue();
                tempYear = orderSeedByTime.getYear();
                tempLocalDateTime = LocalDateTime.of(tempYear, tempMonth, 1, 0, 0, 0);
                result = tempLocalDateTime.plusMonths(1).minusDays(1);
                break;
            case MONTH:
                // nếu theo tháng, năm thì random ngày.
                tempMonth = orderSeedByTime.getMonth().getValue();
                tempYear = orderSeedByTime.getYear();
                tempLocalDateTime = LocalDateTime.of(tempYear, tempMonth, 1, 0, 0, 0);
                LocalDateTime lastDayOfMonth = tempLocalDateTime.plusMonths(1).minusDays(1);
                int randomDay = NumberUtil.getRandomNumber(1, lastDayOfMonth.getDayOfMonth());
                result = LocalDateTime.of(tempYear, tempMonth, randomDay, 0, 0, 0);
                if (result.isAfter(LocalDateTime.now())) {
                    // nếu sau thời gian hiện tại, tức là tháng năm đang thời gian hiện tại
                    randomDay = NumberUtil.getRandomNumber(1, LocalDateTime.now().getDayOfMonth());
                    result = LocalDateTime.of(tempYear, tempMonth, randomDay, 0, 0, 0);
                }
                break;
            case DAY:
                // nếu là ngày thì fix
                result = LocalDateTime.of(orderSeedByTime.getYear(), orderSeedByTime.getMonth(), orderSeedByTime.getDay(), 0, 0, 0);
                break;
        }
        return result;
    }
}
