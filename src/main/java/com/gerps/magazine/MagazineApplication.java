package com.gerps.magazine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class MagazineApplication {

    public static void main(String[] args) {
        SpringApplication.run(MagazineApplication.class, args);
    }
}
