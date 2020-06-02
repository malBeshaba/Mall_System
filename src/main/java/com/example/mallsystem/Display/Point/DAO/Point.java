package com.example.mallsystem.Display.Point.DAO;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Point {
    @Getter @Setter
    private int id;
    @Getter @Setter
    private int user_id;
    @Getter @Setter
    private int point;
}