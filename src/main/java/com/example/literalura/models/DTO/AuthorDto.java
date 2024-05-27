package com.example.literalura.models.DTO;

import com.example.literalura.models.Book;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorDto(Long id, String name, int birth_year, int death_year, List<Book> books) {

    @Override
    public String toString() {
        return "Autor " +
                "Nome = " + name  +
                ",Ano de Nascimento = " + birth_year +
                ",Ano de Falecimento = " + death_year +
                '}';
    }

    public String getName() {
        return name;
    }
}
