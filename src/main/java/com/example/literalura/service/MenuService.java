package com.example.literalura.service;

import com.example.literalura.models.Author;
import com.example.literalura.models.Book;
import com.example.literalura.models.DTO.AuthorDto;
import com.example.literalura.models.DTO.BookApiResponse;
import com.example.literalura.models.DTO.BookDto;
import com.example.literalura.repositories.AuthorRepository;
import com.example.literalura.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class MenuService {

    private String apiUrl;

    private final String SEARCH_API_URL = "https://gutendex.com/books?search=";

    private final String AUTHORYEAR_API_URL = "https://gutendex.com/books?author_year_start=";

    private final String LANGUAGE_API_URL = "https://gutendex.com/books?languages=";

    private final String POPULAR_API_URL = "https://gutendex.com/books?popular=";

    @Autowired
    private ConsumoApi consumoApi;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    Scanner scanner = new Scanner(System.in);

    public void buscarLivroPorTitulo() {
        String apiUrl = SEARCH_API_URL;

        System.out.println("Digite o titulo de um livro");
        String titulo = scanner.nextLine();

        titulo = titulo.trim();

        titulo = removeSpecialCharacters(titulo);

        String url = apiUrl + titulo.replace(" ", "+");
        String httpResponse = consumoApi.obterDados(url);

        ConverterDados converterDados = new ConverterDados();
        try {
            BookApiResponse bookApiResponse = converterDados.obterDados(httpResponse, BookApiResponse.class);
            List<BookDto> books = bookApiResponse.getResults();

            if (books.isEmpty()) {
                System.out.println("Nenhum livro encontrado com o nome " + titulo);
                return;
            }
            BookDto firstBook = books.get(0);

            System.out.println(firstBook.toString());

            saveToDatabase(firstBook);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String removeSpecialCharacters(String input) {
        // Replace special characters with their equivalents
        input = input.replaceAll("[áàâã]", "a");
        input = input.replaceAll("[éèê]", "e");
        input = input.replaceAll("[íìî]", "i");
        input = input.replaceAll("[óòôõ]", "o");
        input = input.replaceAll("[úùû]", "u");
        input = input.replaceAll("[ç]", "c");

        // Remove any remaining special characters
        input = input.replaceAll("[^\\p{ASCII}]", "");

        return input;
    }

    private void saveToDatabase(BookDto bookDto) {
        // Create or update book
        Book book = new Book();
        book.setId(bookDto.id());
        book.setTitle(bookDto.title());
        book.setLanguages(bookDto.languages());
        book.setDonwload_count(bookDto.download_count());
        book = bookRepository.save(book);

        // Iterate through authors
        for (AuthorDto authorDto : bookDto.authors()) {
            // Check if author already exists
            Optional<Author> existingAutor = authorRepository.findByName(authorDto.name());

            Author author;
            if (existingAutor.isPresent()) {
                author = existingAutor.get();
            } else {
                author = new Author();
                author.setName(authorDto.name());
                author.setBirth_year(authorDto.birth_year());
                author.setDeath_year(authorDto.death_year());
                author = authorRepository.save(author);
            }

            // Add book to the author's livros list
            if (!author.getBooks().contains(book)) {
                author.getBooks().add(book);
                authorRepository.save(author);
            }

            if (book.getAuthors() == null) {
                book.setAuthors(author);
                bookRepository.save(book);
            }
        }

    }

    public void buscarLivrosRegistrados() {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            System.out.println("Nenhum Livro Encontrado");
        } else {
            for (Book book : books) {
                System.out.println(book.toString());
            }
        }
    }

    public void buscarAutoresRegistrados() {
        List<Author> authors = authorRepository.findAll();
        if (authors.isEmpty()) {
            System.out.println("Nenhum Autor Encontrado");
        } else {
            for (Author author : authors) {
                System.out.println(author.toString());
            }
        }
    }

    public void listarAutoresVivos() {
        System.out.println("Insira o ano de nascimento desejado");
        String ano = scanner.next();
        String url = AUTHORYEAR_API_URL + ano;
        String httpResponse = consumoApi.obterDados(url);
        ConverterDados converterDados = new ConverterDados();

        try {
            BookApiResponse bookApiResponse = converterDados.obterDados(httpResponse, BookApiResponse.class);
            List<BookDto> books = bookApiResponse.getResults();

            if (books.isEmpty()) {
                System.out.println("Nenhum Autor Encontrado");
            }
            for (BookDto book : books) {
                book.getAuthors().forEach(author -> System.out.println(author.getName()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void buscarLivrosIdioma() {
        System.out.println("""
                Insira o idioma que gostaria de buscar:
                                
                en - Ingles
                pt - Portugues
                es - Espanhol
                fr - Francês
                de - Alemanha
                """);
        String idioma = scanner.next();
        String url = LANGUAGE_API_URL + idioma;
        String httpResponse = consumoApi.obterDados(url);
        ConverterDados converterDados = new ConverterDados();

        try {
            BookApiResponse bookApiResponse = converterDados.obterDados(httpResponse, BookApiResponse.class);
            List<BookDto> books = bookApiResponse.getResults();

            if (books.isEmpty()) {
                System.out.println("Nenhum Livro Encontrado");
            }
            for (BookDto book : books) {
                System.out.println(book.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void buscarLivrosMaisBaixados() {

        String httpResponse = consumoApi.obterDados(POPULAR_API_URL);
        ConverterDados converterDados = new ConverterDados();

        try {
            BookApiResponse bookApiResponse = converterDados.obterDados(httpResponse, BookApiResponse.class);
            List<BookDto> books = bookApiResponse.getResults();

            int count = 0;
            for (BookDto book : books) {
                if (count > 9) {
                    break;
                }
                System.out.println(book.toString());
                count++;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

