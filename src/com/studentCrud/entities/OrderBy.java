package com.studentCrud.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor@Getter
public class OrderBy {
    private String columnName;
    private order order;

    @Override
    public String toString() {
        return " ORDER BY " + "student."+columnName +" " + order.toString();
    }
}
