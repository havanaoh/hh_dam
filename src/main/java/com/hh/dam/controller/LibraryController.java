package com.hh.dam.controller;

import com.hh.dam.dto.BookDTO;
import com.hh.dam.entity.Library;
import com.hh.dam.entity.Member;
import com.hh.dam.service.BookService;
import com.hh.dam.service.LibraryService;
import com.hh.dam.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private BookService bookService;


    @GetMapping("/my")
    public String library(){
        return "library/my-library";
    }

    @GetMapping("/wishlist")
    public String viewWishList(Model model, Principal principal) {
        Member member = memberService.findByUserId(principal.getName());
        List<Library> wishList = libraryService.getWishListBooks(member);

        model.addAttribute("wishList", wishList);
        model.addAttribute("isEmpty", wishList.isEmpty());
        return "library/wishlist";
    }

    @PostMapping("/wishlist/remove")
    public ResponseEntity<String> removeFromWishlist(@RequestParam int bookId, Principal principal) {
        Member member = memberService.findByUserId(principal.getName());
        System.out.println("Received remove request - Member: " + member.getUserId() + ", Book ID: " + bookId);

        boolean success = libraryService.removeFromWishlist(member, bookId);

        if (success) {
            System.out.println("Successfully removed from wishlist.");
            return ResponseEntity.ok("찜 해제 성공");
        } else {
            System.out.println("Failed to remove from wishlist.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("찜 해제 실패");
        }
    }

    @PostMapping("/add-wishlist/{itemId}")
    @ResponseBody
    public String addBookToWishList(@PathVariable("itemId") int itemId, @RequestParam("query") String query, Principal principal) {
        // 현재 로그인한 회원 정보 가져오기
        Member member = memberService.findByUserId(principal.getName());

        // 특정 itemId로 검색된 책 정보 가져오기
        BookDTO bookDTO = bookService.findBookByItemId(query, itemId);

        if (bookDTO != null && !libraryService.isBookRegistered(bookDTO.getIsbn())) {
            libraryService.addBookToDatabaseAndLibrary(bookDTO, member);
        }

        return "redirect:/library/wishlist";  // 찜 목록 페이지로 리다이렉트
    }

    @GetMapping("/reading")
    public String viewReadingList(Model model, Principal principal) {
        Member member = memberService.findByUserId(principal.getName());
        List<Library> readingList = libraryService.getReadingBooks(member);

        model.addAttribute("readingList", readingList);
        model.addAttribute("isEmpty", readingList.isEmpty());
        return "library/reading";
    }



}
