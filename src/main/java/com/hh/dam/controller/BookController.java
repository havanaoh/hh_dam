package com.hh.dam.controller;

import com.hh.dam.entity.BookStatus;
import com.hh.dam.service.BookService;
import com.hh.dam.service.LibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    private final LibraryService libraryService;

    public BookController(BookService bookService, LibraryService libraryService) {
        this.bookService = bookService;
        this.libraryService = libraryService;
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

    @PutMapping("/{bookId}/status")
    public ResponseEntity<?> changeBookStatus(@PathVariable int bookId, @RequestParam BookStatus newStatus) {
        libraryService.updateBookStatus(bookId, newStatus);
        return ResponseEntity.ok("Book status updated");
    }

}
