package com.hh.dam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LibraryDTO {
    private int libraryId;
    private String bookTitle;
    private int currentPage;

}
