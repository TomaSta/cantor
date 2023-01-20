package com.stasko.tomasz.cantor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CantorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CantorApplication.class, args);
    }

}
