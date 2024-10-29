package com.hh.dam.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hh.dam.dto.BookDTO;
import com.hh.dam.entity.Book;
import com.hh.dam.entity.BookStatus;
import com.hh.dam.entity.Library;
import com.hh.dam.entity.Member;
import com.hh.dam.repository.BookRepository;
import com.hh.dam.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private LibraryRepository libraryRepository;


    @Value("${aladin.api.key}")
    private String aladinApiKey;

    // 찜 목록에 있는 책들 가져오기 (특정 회원의 찜 목록 조회)
    public List<Library> getWishListBooks(Member member) {
        // 해당 회원의 찜 목록에서 '찜' 상태인 책들만 반환
        return libraryRepository.findByMemberAndStatus(member, BookStatus.찜);
    }

    public boolean isBookRegistered(String isbn) {
        return bookRepository.existsByIsbn(isbn);
    }


    @Transactional
    public void addBookToDatabaseAndLibrary(BookDTO bookDTO, Member member) {
        // BookDTO 정보를 이용해 Book 엔티티 생성
        Book book = new Book();
        book.setBookTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPublisher(bookDTO.getPublisher());
        book.setCover(bookDTO.getCover());
        book.setIsbn(bookDTO.getIsbn());
        book.setTotalPage(bookDTO.getTotalPage());

        // DB에 Book 저장
        Book savedBook = bookRepository.save(book);

        // Library 엔티티 생성 및 설정
        Library library = new Library();
        library.setBook(savedBook);
        library.setMember(member);
        library.setStatus(BookStatus.찜); // 기본적으로 찜 목록 상태로 설정

        // 서재에 추가
        libraryRepository.save(library);
    }

    public boolean removeFromWishlist(Member member, int bookId) {
        System.out.println("Removing from wishlist - Member: " + member.getUserId() + ", Book ID: " + bookId);

        // bookId로 Book 엔티티를 먼저 찾아야 함
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            // 해당 member와 book을 사용하여 Library 엔티티 검색
            Optional<Library> libraryOptional = libraryRepository.findByBookAndMember(book, member);

            if (libraryOptional.isPresent()) {
                libraryRepository.delete(libraryOptional.get()); // 삭제
                return true;
            }
        }
        return false; // 삭제할 항목이 없으면 false 반환
    }


    public Library getLibraryByBookIdAndMember(int bookId, Member member) {
        // 먼저 bookId로 Book 객체를 조회
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("해당 책을 찾을 수 없습니다."));

        // Book 객체와 Member로 Library 조회
        return libraryRepository.findByBookAndMember(book, member)
                .orElseThrow(() -> new RuntimeException("해당 도서 기록을 찾을 수 없습니다."));
    }

    @Transactional
    public void updateReadingProgress(int libraryId, int currentPage) {
        Library library = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new RuntimeException("도서를 찾을 수 없습니다."));
        library.setCurrentPage(currentPage); // 읽은 페이지 수 업데이트

        // 독서율 계산
        int totalPage = library.getBook().getTotalPage();
        double readingProgress = ((double) currentPage / totalPage) * 100;

        // 책 상태를 '읽는 중'으로 변경
        library.setStatus(BookStatus.읽는중);

        // 독서율이 100%면 상태를 '완독'으로 변경
        if (currentPage == totalPage) {
            library.setStatus(BookStatus.완독);
        }

        libraryRepository.save(library);
    }



}
