package com.hh.dam.service;

import com.hh.dam.entity.Book;
import com.hh.dam.entity.BookStatus;
import com.hh.dam.entity.Library;
import com.hh.dam.repository.BookRepository;
import com.hh.dam.repository.LibraryRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class LibraryService {

    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;

    public LibraryService(BookRepository bookRepository, LibraryRepository libraryRepository) {
        this.bookRepository = bookRepository;
        this.libraryRepository = libraryRepository;
    }

    public void updateBookStatus(int bookId, BookStatus newStatus) {
        // 책을 조회
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));

        // 해당 책의 라이브러리 목록을 가져옴
        List<Library> libraries = book.getLibraries();

        // 모든 라이브러리에서 상태를 업데이트
        for (Library library : libraries) {
            library.setStatus(newStatus); // 상태 업데이트
            library.setEndDate(new Timestamp(System.currentTimeMillis())); // 수정 시간 기록
            // 각 라이브러리 저장
            libraryRepository.save(library);
        }
    }
}
