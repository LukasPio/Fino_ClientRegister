package com.lucas.clientregister.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientRegisterApplication {

    public static final Logger applicationLogger = LoggerFactory.getLogger(ClientRegisterApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(ClientRegisterApplication.class, args);
    }

}
