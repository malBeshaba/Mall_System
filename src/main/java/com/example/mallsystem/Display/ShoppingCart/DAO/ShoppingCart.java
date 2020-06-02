package com.example.mallsystem.Display.ShoppingCart.DAO;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {
    @Setter @Getter
    private int id;
    @Setter @Getter @NonNull
    private int user_id;
    @Setter @Getter @NonNull
    private int goods_id;

}