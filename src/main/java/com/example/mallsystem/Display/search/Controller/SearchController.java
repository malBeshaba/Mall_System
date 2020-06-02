package com.example.mallsystem.Display.search.Controller;

import com.example.mallsystem.Display.search.Service.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {
    private SearchService service;
    @GetMapping(
            value = "/search",
            produces = "application/json;" + "charset=utf-8"
    )
    public String search(@RequestParam(value = "param") String param) {
        service = new SearchService(param);
        return service.toJson();
    }
}