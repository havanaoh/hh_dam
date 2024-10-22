package com.hh.dam.controller;

import com.hh.dam.entity.Board;
import com.hh.dam.entity.Notifications;
import com.hh.dam.repository.BoardRepository;
import com.hh.dam.repository.NotificationsRepository;
import com.hh.dam.service.BoardService;
import com.hh.dam.service.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private NotificationsRepository notificationsRepository;

    @GetMapping("/faq")
    public String faqList(Model model){
        return "faqList";
    }

    @GetMapping("/notifications")
    public List<Notifications> getMemberNotifications(@RequestParam int memberId) {
        return notificationsRepository.findAllByMember_MemberIdAndIsReadFalse(memberId);
    }


}
