package org.example.stablecoinchecker;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@EnableBatchProcessing
public class StableCoinCheckerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StableCoinCheckerApplication.class, args);
    }

}
