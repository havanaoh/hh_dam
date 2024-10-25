package com.hh.dam.service;

import com.hh.dam.dto.AladinApiResponse;
import com.hh.dam.dto.BookDTO;
import com.hh.dam.entity.Book;
import com.hh.dam.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@Transactional
public class BookService {

    @Value("{aladin.api.key}")
    private String ttbKey;

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDTO> searchBooks(String query) {
        RestTemplate restTemplate = new RestTemplate();
        String ALADIN_API_URL = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx";
        String url = ALADIN_API_URL + "?ttbkey=" + ttbKey + "&Query=" + query + "&output=js";

        // API 호출 및 결과 받아오기
        AladinApiResponse response = restTemplate.getForObject(url, AladinApiResponse.class);

        // 응답 결과를 BookDTO 리스트로 변환 후 반환
        return response.getItem();  // 필요에 따라 변환 로직 추가
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
                            .bookCover(list.getString("cover"))
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
