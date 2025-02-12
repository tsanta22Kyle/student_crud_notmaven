package com.studentCrud.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
@NoArgsConstructor
@Setter
@AllArgsConstructor@Getter@EqualsAndHashCode
public class Student extends StudentBase {
    private String id;



    public Student(String sex, Timestamp birthdate, String group, String firstname, String lastname, String reference, String id) {
        super(sex, birthdate, group, firstname, lastname, reference);
        this.id = id;
    }





    @Override
    public String toString(){
        return this.getFirstname()+" is "+ this.getLastname() + " , "+ this.getId() + " , "+ this.getBirthdate()+" sex : "+ this.getSex()+" in "+this.getGroup() + " as "+ this.getReference();
    }
}
