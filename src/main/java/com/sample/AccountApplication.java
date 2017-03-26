package com.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Account microservices
 *
 * The main target of this microservice is show with tdd how a microservice works
 * with Spring Boot for the bus training session.
 *
 * @author Noel Rodriguez
 */
@SpringBootApplication
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }
}
