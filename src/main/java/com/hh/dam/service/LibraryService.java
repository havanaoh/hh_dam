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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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

    public List<Library> getReadingBooks(Member member){
        return libraryRepository.findByMemberAndStatus(member, BookStatus.읽는중);
    }

    public boolean isBookRegistered(String isbn) {
        return bookRepository.existsByIsbn(isbn);
    }


    @Transactional
    public void addBookToDatabaseAndLibrary(BookDTO bookDTO, Member member) {
        log.info("입력받은 ISBN: {}", bookDTO.getIsbn());
        // 1. ISBN으로 책을 조회하여 DB에 이미 등록되어 있는지 확인
        Optional<Book> existingBook = bookRepository.findByIsbn(bookDTO.getIsbn());
        log.info("DB에서 ISBN {}으로 조회한 책: {}", bookDTO.getIsbn(), existingBook);
        Book book;
        if (existingBook.isPresent()) {
            // 책이 이미 DB에 있을 경우
            book = existingBook.get();
            log.info("책이 이미 DB에 존재합니다. 책 ID: {}", book.getBookId());
        } else {
            // 책이 DB에 없을 경우 새로운 Book 엔티티 생성 및 저장
            book = new Book();
            book.setBookTitle(bookDTO.getTitle());
            book.setAuthor(bookDTO.getAuthor());
            book.setPublisher(bookDTO.getPublisher());
            book.setCover(bookDTO.getCover());
            book.setIsbn(bookDTO.getIsbn());
            book.setTotalPage(bookDTO.getTotalPage());
            book = bookRepository.save(book);  // DB에 저장
            log.info("책이 DB에 저장되었습니다. 책 ID: {}", book.getBookId());
        }

        // 2. 해당 회원의 서재에 이미 책이 있는지 확인
        boolean alreadyInLibrary = libraryRepository.existsByMemberAndBook(member, book);

        if (!alreadyInLibrary) {
            // 서재에 책이 없을 경우에만 Library 엔티티를 생성하여 저장
            Library library = new Library();
            library.setBook(book);
            library.setMember(member);
            library.setStatus(BookStatus.찜); // 기본적으로 찜 상태로 설정
            libraryRepository.save(library);  // 서재에 추가
        } else {
            // 서재에 이미 책이 있을 경우 로직
            System.out.println("이 책은 이미 회원의 서재에 등록되어 있습니다.");
        }
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
