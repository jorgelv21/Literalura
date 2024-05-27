package com.example.literalura.principal;

import com.example.literalura.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;


@Component
public class Principal {


    @Autowired
    private MenuService menuService;


    Scanner scanner = new Scanner(System.in);
    boolean menu = true;

    public void exibeMenu() {
        while (menu) {
            System.out.println("""
                    Escolha uma das opções abaixo:
                                    
                    1 - Selecionar livro por titulo
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - listar livros em um determinado idioma
                    6 - Buscar os 10 livros mais baixados
                    0 - Sair
                                    
                    """);

            int opcao = 1;
            opcao = scanner.nextInt();
            switch (opcao) {
                case 1:
                    menuService.buscarLivroPorTitulo();
                    break;
                case 2:
                    menuService.buscarLivrosRegistrados();
                    break;
                case 3:
                    menuService.buscarAutoresRegistrados();
                    break;
                case 4:
                    menuService.listarAutoresVivos();
                    break;
                case 5:
                    menuService.buscarLivrosIdioma();
                    break;
                case 6:
                    menuService.buscarLivrosMaisBaixados();
                    break;
                case 0:
                    menu = false;
                    break;
                default:
                    System.out.println("Opção invalida, tente novamente");
            }
        }
    }

}
