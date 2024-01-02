package ru.kraldraav.autopartsfinder.web.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kraldraav.autopartsfinder.AutoPartsFinderApplication;

@RestController
public class MainController {
    @GetMapping(value="/")
    public String index(){
        return "%s running!!".formatted(AutoPartsFinderApplication.class.getName());
    }
}
