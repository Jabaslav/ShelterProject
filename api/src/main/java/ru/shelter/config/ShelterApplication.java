package ru.shelter.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "ru.shelter")
@EnableJpaRepositories("ru.shelter.interfaces")
@EntityScan(basePackages = "ru.shelter.model")
public class ShelterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShelterApplication.class, args);
    }

}
