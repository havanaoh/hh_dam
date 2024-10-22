package com.hh.dam.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

@Service
public class LibraryService {

    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;

    @Autowired
    public LibraryService(BookRepository bookRepository, LibraryRepository libraryRepository) {
        this.bookRepository = bookRepository;
        this.libraryRepository = libraryRepository;
    }

    @Value("${aladin.api.key}")
    private String aladinApiKey;

    // 찜 목록에 있는 책들 가져오기 (특정 회원의 찜 목록 조회)
    public List<Library> getWishListBooks(Member member) {
        // 해당 회원의 찜 목록에서 '찜' 상태인 책들만 반환
        return libraryRepository.findByMemberAndStatus(member, BookStatus.찜);
    }

    public List<Book> searchBooks(String query) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=%s&Query=%s&QueryType=Keyword&MaxResults=10&Start=1&SearchTarget=Book&Output=JS",
                aladinApiKey, query);

        // 알라딘 API로부터 JSON 응답을 받기
        String response = restTemplate.getForObject(url, String.class);

        // JSON 응답을 파싱하여 Book 객체 리스트로 변환 (여기서 JSON 파싱 로직 필요)
        // 아래는 가상의 메서드로, 실제 JSON 응답 형태에 맞게 구현해야 합니다.
        List<Book> books = parseJsonToBooks(response);
        return books;
    }

    // JSON 응답을 Book 리스트로 변환하는 메서드
    private List<Book> parseJsonToBooks(String json) {
        // JSON 파싱 로직 구현
        // 이곳에 JSON 라이브러리를 사용하여 Book 객체로 변환하는 로직을 추가합니다.
        Gson gson = new Gson();
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        JsonArray itemArray = jsonObject.getAsJsonArray("item"); // 'item' 배열에서 책 정보를 가져온다고 가정

        List<Book> books = new ArrayList<>();
        for (int i = 0; i < itemArray.size(); i++) {
            JsonObject item = itemArray.get(i).getAsJsonObject();

            Book book = new Book();
            book.setBookTitle(item.get("title").getAsString());
            book.setAuthor(item.get("author").getAsString());
            book.setPublisher(item.get("publisher").getAsString());
            book.setIsbn(item.get("isbn").getAsString());
            book.setTotalPage(item.get("totalPage").getAsInt()); // totalPage 필드가 있다면
            book.setBookCover(item.get("cover").getAsString()); // cover 이미지 URL
            books.add(book);
        }
        return books;
    }

    // 찜 목록에 책 추가
    public void addBookToWishList(int bookId, Member member) {
        // bookId로 책 찾기
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("책을 찾을 수 없습니다."));

        // 해당 책을 회원의 찜 목록에 추가 (Library 엔티티 생성)
        Library library = new Library();
        library.setBook(book);
        library.setMember(member);
        library.setStatus(BookStatus.찜);  // 상태를 '찜'으로 설정
        library.setStartDate(LocalDate.now());  // 찜 목록에 추가한 날짜
        libraryRepository.save(library);  // Library 저장
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
