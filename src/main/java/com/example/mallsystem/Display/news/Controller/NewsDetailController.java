package com.example.mallsystem.Display.news.Controller;

import com.example.mallsystem.Display.news.DAO.NewsDBManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewsDetailController {
    NewsDBManager manager;

    @GetMapping(
            value = "/news/detail"
    )
    public String getDetail(@RequestParam(value = "news_id") int id) {
        manager = new NewsDBManager();
        return manager.selectDetail(id);
    }
}