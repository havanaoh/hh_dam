package com.hh.dam.controller;

import com.hh.dam.dto.BookDTO;
import com.hh.dam.dto.CustomMemberDetails;
import com.hh.dam.dto.LibraryDTO;
import com.hh.dam.entity.Library;
import com.hh.dam.entity.Member;
import com.hh.dam.service.BookService;
import com.hh.dam.service.LibraryService;
import com.hh.dam.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    private final LibraryService libraryService;
    @Autowired
    private MemberService memberService;

    public BookController(BookService bookService, LibraryService libraryService) {
        this.bookService = bookService;
        this.libraryService = libraryService;
    }

    @GetMapping("/api/library/search")
    @ResponseBody
    public List<BookDTO> searchBooks(@RequestParam("query") String query) {
        // BookService를 이용해 책 검색 수행
        return bookService.searchBooks(query);
    }

    @GetMapping("/api/library/pages")
    @ResponseBody
    public int bookPages(@RequestParam("itemId") int itemId) {
        return bookService.bookPages(itemId);
    }

    @GetMapping("/addBookForm")
    public String showAddBooksForm() {
        return "book/addBookForm";  // 입력 폼 페이지로 이동
    }

    @GetMapping("/addBooks")
    public String addBooks(@RequestParam String QueryType, Model model) {
        // BookService를 통해 API로부터 책 데이터 가져오기
        int resultCount = bookService.addBook(QueryType);
        model.addAttribute("resultCount", resultCount);  // 뷰에서 사용할 데이터 전달
        return "book/resultBookAdd";
    }

    // 읽은 페이지 입력 폼을 Ajax로 제공하는 메서드
    @GetMapping("/library/read/{bookId}")
    @ResponseBody
    public LibraryDTO getReadingForm(@PathVariable int bookId, @AuthenticationPrincipal CustomMemberDetails userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("User not authenticated");
        }
        Member member = userDetails.getMember(); // CustomMemberDetails에서 Member 가져오기
        Library library = libraryService.getLibraryByBookIdAndMember(bookId, member);
        return new LibraryDTO(library.getLibraryId(), library.getBook().getBookTitle(), library.getCurrentPage());
    }
    // 읽은 페이지 수를 업데이트하는 메서드
    @PostMapping("/library/update-progress")
    @ResponseBody
    public ResponseEntity<?> updateReadingProgress(@RequestParam int bookId, @RequestParam int currentPage) {
        libraryService.updateReadingProgress(bookId, currentPage);
        return ResponseEntity.ok().build();  // 성공 시 200 OK 반환
    }


}
