package com.hh.dam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AladinApiResponse {
    @JsonProperty("item")
    private List<BookDTO> item;

    // Getters and Setters
}
