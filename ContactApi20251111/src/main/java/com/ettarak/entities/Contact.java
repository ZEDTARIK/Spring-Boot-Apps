package com.ettarak.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
//@JsonInclude(NON_DEFAULT)
public class Contact {
    @Id
    @UuidGenerator
    @Column(unique = true, nullable = false)
    private String id;
    @Column(length = 50)
    private String name;
    @Column(length = 50)
    private String email;
    @Column(length = 50)
    private String title;
    @Column(length = 50)
    private String phone;
    @Column(length = 50)
    private String address;
    @Column(length = 20)
    private String status;
    private String photoUrl;

}
