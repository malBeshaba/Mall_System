package com.example.mallsystem.Display.goods.DAO;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    @Getter @Setter
    private int id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String business;
    @Getter @Setter
    private int volume;
    @Getter @Setter
    private double price;
    @Getter @Setter
    private String detail;
    @Getter @Setter
    private String imageUrl;
}