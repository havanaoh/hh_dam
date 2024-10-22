package com.hh.dam.controller;

import com.hh.dam.entity.Book;
import com.hh.dam.entity.Library;
import com.hh.dam.entity.Member;
import com.hh.dam.service.LibraryService;
import com.hh.dam.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/library")
public class LibraryController {

    final private LibraryService libraryService;
    final private MemberService memberService;

    public LibraryController(LibraryService libraryService, MemberService memberService) {
        this.libraryService = libraryService;
        this.memberService = memberService;
    }

    @GetMapping("/my")
    public String library(){
        return "library/my-library";
    }

    @GetMapping("/wishlist")
    public String viewWishList(Model model, Principal principal) {
        // 현재 로그인한 회원 정보 가져오기
        Member member = memberService.findByUserId(principal.getName());
        List<Library> wishList = libraryService.getWishListBooks(member); // 찜 목록 가져오기
        model.addAttribute("wishList", wishList); // 모델에 찜 목록 추가
        return "library/wishlist"; // 찜 목록 뷰 페이지로 이동
    }

    @PostMapping("/add-wishlist/{id}")
    public String addBookToWishList(@PathVariable("id") int bookId, Principal principal) {
        // 현재 로그인한 회원 정보 가져오기
        Member member = memberService.findByUserId(principal.getName());
        libraryService.addBookToWishList(bookId, member); // 찜 목록에 책 추가
        return "redirect:/library/wishlist";  // 찜 목록 페이지로 리다이렉트
    }





}
