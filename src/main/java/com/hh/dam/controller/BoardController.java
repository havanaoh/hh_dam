package com.hh.dam.controller;

import com.hh.dam.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {


    @GetMapping("/faq")
    public String faqList(Model model){
        return "faqList";
    }
}
