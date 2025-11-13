package com.ettarak.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class Note {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private String codeId;

    @Column(length = 20)
    private String title;
    @Column(length = 50)
    private String content;

    private Date created;

    @PrePersist
    public void prePersist()
    {
        created = new Date();
        this.codeId = UUID.randomUUID().toString();
    }

}
