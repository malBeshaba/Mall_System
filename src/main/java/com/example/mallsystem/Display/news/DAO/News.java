package com.example.mallsystem.Display.news.DAO;

import lombok.*;

@AllArgsConstructor
@ToString
@NoArgsConstructor
public class News {
    @Getter @Setter
    private int id;
    @Getter @Setter
    private String title;
    @Getter @Setter
    private String detail;
}