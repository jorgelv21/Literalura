package com.example.literalura.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "livros")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int donwload_count;

    private List<String> languages;


    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Author authors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDonwload_count() {
        return donwload_count;
    }

    public void setDonwload_count(int donwload_count) {
        this.donwload_count = donwload_count;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }


    public Author getAuthors() {
        return authors;
    }

    public void setAuthors(Author authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "Book " +
                "Titulo='" + title +
                ", Numero de Downloads = " + donwload_count +
                ", Idioma = '" + languages +
                ", Autores = " + authors.getName() ;
    }
}
