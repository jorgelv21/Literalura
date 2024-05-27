package com.example.literalura.models.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookDto(Long id, String title, int download_count, List<AuthorDto> authors, List<String> languages) {

    @Override
    public String toString() {
        return "Titulo = '" + title + '\'' +
                ", Quantidade de downloads = " + download_count +
                ", Autores = " + authors +
                ", Idiomas = " + languages +
                '}';
    }

    public List<AuthorDto> getAuthors() {
        return authors;
    }
}