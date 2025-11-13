package com.ettarak;

import com.ettarak.entities.Note;
import com.ettarak.repository.NoteRepository;
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
    CommandLineRunner init(NoteRepository noteRepository) {
        return args -> {
            noteRepository.save(Note.builder().title("Title 1").content("Content 1").build());
            noteRepository.save(Note.builder().title("Title 2").content("Content 2").build());
        };
    }
}
