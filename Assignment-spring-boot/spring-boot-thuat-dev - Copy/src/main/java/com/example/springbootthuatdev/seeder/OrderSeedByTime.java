package com.example.springbootthuatdev.seeder;

import com.example.springbootthuatdev.entity.enums.OrderSimpleStatus;
import lombok.*;

import java.time.Month;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderSeedByTime {
    // Loai gom 3 kieu:
    // Theo ngay (generate chinh xac theo ngay thang nam)
    // Theo thang (generate chinh xac theo thang va nam, ngay sinh random)
    // Theo nam (generate chinh xac theo nam, ngay sinh va thang random)
    private OrderSeedByTimeType seedTypeByTime;
    private int year; // nam nao
    private Month month; // thang nao
    private int day; // ngay nao
    private int orderCount; // so luong order trong thoi gian nay
    private OrderSimpleStatus orderStatus; // trang thai order can sinh.
}
