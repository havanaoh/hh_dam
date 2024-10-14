package com.hh.dam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String main() {
        return "index";  // templates/public/index.html로 이동
    }

    @GetMapping("/challenge")
    public String challenge() {
        return "challenge";  // templates/public/index.html로 이동
    }
}
