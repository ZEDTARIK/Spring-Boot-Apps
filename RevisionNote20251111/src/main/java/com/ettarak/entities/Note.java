package com.ettarak.entities;

import com.ettarak.enums.Level;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class Note implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    @NotNull(message = "The note can not be null !!") @NotEmpty(message = "The note can not be empty !!")
    private String title;
    @Column(length = 100)
    private String content;

    @Enumerated(EnumType.STRING)
    private Level level;

    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime createAt;

    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime updateAt;

    @PrePersist
    public void prePersist(){
        this.createAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.updateAt = LocalDateTime.now();
    }
}
