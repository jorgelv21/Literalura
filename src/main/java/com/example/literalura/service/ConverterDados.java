package com.example.literalura.service;

import com.example.literalura.models.Book;
import com.example.literalura.models.DTO.BookApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverterDados implements IConverterDados {


    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return objectMapper.readValue(json, classe);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
