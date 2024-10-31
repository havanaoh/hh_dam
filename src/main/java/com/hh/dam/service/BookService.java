package com.hh.dam.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.dam.dto.AladinApiResponse;
import com.hh.dam.dto.BookDTO;
import com.hh.dam.entity.Book;
import com.hh.dam.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class BookService {

    @Value("${aladin.api.key}")
    private String ttbKey;

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDTO> searchBooks(String query) {
        // encode 되어있는 경우 decode
        try{
            query = java.net.URLDecoder.decode(query, "UTF-8");
        } catch (Exception e) {
            // 건너뛰기
        }
        RestTemplate restTemplate = new RestTemplate();
        String ALADIN_API_URL = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx";
        String url = ALADIN_API_URL + "?ttbkey=" + ttbKey + "&query=" + query +
                "&QueryType=Keyword&MaxResults=10&start=1&SearchTarget=Book&output=js&Version=20131101";

        // API 호출 및 결과 받아오기
        AladinApiResponse response = restTemplate.getForObject(url, AladinApiResponse.class);

        // 응답 결과를 BookDTO 리스트로 변환 후 반환
        log.info("Aladin API 응답: {}", response);
        return response.getItem();  // 필요에 따라 변환 로직 추가
    }

    public int bookPages(int itemId) {
        RestTemplate restTemplate = new RestTemplate();
        String ALADIN_API_URL = "http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx";
        String url = ALADIN_API_URL + "?ttbkey=" + ttbKey + "&itemIdType=ItemId&ItemId=" + itemId +
                "&output=js&Version=20131101&OptResult=itemPage";

        // API 호출 및 결과 받아오기
        AladinApiResponse response = restTemplate.getForObject(url, AladinApiResponse.class);

        // 응답 결과에서 페이지 수 반환
        if (response != null && response.getItem() != null && !response.getItem().isEmpty()) {
            return response.getItem().get(0).getSubInfo().getItemPage(); // itemPage를 올바르게 접근
        } else {
            log.warn("응답 결과가 null이거나 빈 리스트입니다: {}", response);
            return 0; // 아이템이 없을 경우 0 반환
        }
    }

    public BookDTO findBookByItemId(String query, int itemId) {
        List<BookDTO> searchResults = searchBooks(query);

        // itemId로 검색 결과에서 특정 책 찾기
        return searchResults.stream()
                .filter(book -> book.getItemId() == itemId)
                .findFirst()
                .orElse(null);
    }


    //지울 예정?
    public int addBook(String QueryType){

        String apiURL = "http://www.aladin.co.kr/ttb/api/ItemList.aspx";
        String ttbkey = "ttbhyelin20250322001";
        int count = 0;

        for(int i = 1; i <= 6; i++) {

            StringBuilder sb = new StringBuilder();
            sb.append(apiURL);
            sb.append("?ttbkey=" + ttbkey);
            sb.append("&QueryType=" + QueryType);
            sb.append("&MaxResults=50");
            sb.append("&Start=" + i);
            sb.append("&SearchTarget=Book");
            sb.append("&output=JS");
            sb.append("&Cover=Big");
            sb.append("&Version=20131101");

            JSONObject obj = null;
            try {
                // 요청
                URL url = new URL(sb.toString());
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");  // 반드시 대문자로 작성

                // 응답
                BufferedReader reader = null;
                int responseCode = con.getResponseCode();
                if(responseCode == 200) {
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {
                    reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String line = null;
                StringBuilder responseBody = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    responseBody.append(line);
                }
                obj = new JSONObject(responseBody.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            JSONArray array = (JSONArray)obj.get("item");

            // for문으로 값 하나씩 insert하기
            for (Object lists : array) {
                try {
                    JSONObject list = (JSONObject) lists;
                    String pubdate = list.getString("pubDate");
                    Date date = java.sql.Date.valueOf(pubdate);

                    Book book = Book.builder()
                            .isbn(list.getString("isbn13"))
                            .bookTitle(list.getString("title"))
                            .cover(list.getString("cover"))
                            .author(list.getString("author"))
                            .publisher(list.getString("publisher"))
                            .build();
                    bookRepository.save(book);  // JPA를 이용한 데이터 저장
                    count++;
                } catch (Exception e) {
                    e.printStackTrace();
                    count--;
                }
            }
        }
        return count;
    }

}
