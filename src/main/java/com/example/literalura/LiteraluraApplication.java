package com.example.literalura;

import com.example.literalura.principal.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

    @Autowired
    private Principal principal;

    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    public void run(String... args) {
        principal.exibeMenu();
    }

}
