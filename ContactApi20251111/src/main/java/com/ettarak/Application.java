package com.ettarak;

import com.ettarak.entities.Contact;
import com.ettarak.repositories.ContactRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    CommandLineRunner init(ContactRepository contactRepository) {
        return args -> {
            contactRepository.save(Contact.builder().name("John").email("John@email.fr").phone("02548748").address("USA").build());
            contactRepository.save(Contact.builder().name("Zouhair").email("Zouhair@email.fr").phone("065487495").address("Morocco").build());
        };
    }

}
