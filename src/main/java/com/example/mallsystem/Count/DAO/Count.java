package com.example.mallsystem.Count.DAO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Count {
    @Getter @Setter
    private int goods_id;
    @Getter @Setter
    private int daily_sales;
    @Getter @Setter
    private int monthly_sales;
    @Getter @Setter
    private int annual_sales;
}