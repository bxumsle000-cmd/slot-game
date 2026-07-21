package com.poz.slot_game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 的進入點。
 * <p>
 * @SpringBootApplication 放在 com.poz.slot_game 這一層，
 * Spring 會自動掃描這個 package 底下所有子 package
 * （engine、service、controller、model），把標了
 * @Service、@RestController 的類別都建立成 bean。
 */
@SpringBootApplication
public class SlotGameApplication {

    public static void main(String[] args) {
        SpringApplication.run(SlotGameApplication.class, args);
    }
}
