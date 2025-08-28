package com.varshith.filemetadata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FilemetadataApplicationTests {
    public static void main(String[] args) {
        SpringApplication
                .from(FilemetadataApplication::main)
                .with(DaprTestContainersConfig.class)
                .run(args);
    }
}