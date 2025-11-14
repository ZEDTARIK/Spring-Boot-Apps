package com.ettarak.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class Employee {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private String employeeCode;

    @NotBlank(message = "The employee name can not be blank !!")
    private String employeeName;

    @Email(message = "The format email is not correct, please try again")
    @NotBlank(message = "The employee email can not be blank !!")
    private String employeeEmail;

    private Date createDate;
    private Date updateDate;
    private Date deletionDate;

    @PrePersist
    public void prePersist()
    {
        this.createDate = new Date();
    }

    @PreUpdate
    public void preUpdate()
    {
        this.updateDate = new Date();
    }

    @PreRemove
    public void preRemove()
    {
        this.deletionDate = new Date();
    }
}
