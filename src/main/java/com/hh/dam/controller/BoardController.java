package com.hh.dam.controller;

import com.hh.dam.entity.Board;
import com.hh.dam.entity.Notifications;
import com.hh.dam.repository.NotificationsRepository;
import com.hh.dam.service.BoardService;
import com.hh.dam.service.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class BoardController {

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

    @GetMapping("/create")
    public String createBoardForm(Model model) {
        model.addAttribute("board", new Board());
        return "boards/create-board";
    }

    @GetMapping("/edit/{boardId}")
    public String editBoardForm(@PathVariable int boardId, Model model) {
        Optional<Board> board = boardService.getBoardById(boardId);
        if (board.isPresent()) {
            model.addAttribute("board", board.get());
            return "boards/edit-board";
        } else {
            return "redirect:/boards";
        }
    }


}
