package com.studentCrud.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
@Setter@EqualsAndHashCode@ToString
@AllArgsConstructor@NoArgsConstructor@Getter
public abstract class StudentBase {



    private String sex;
    private Timestamp birthdate;
    private String group;
    private String firstname;
    private String lastname;
    private String reference;


}
