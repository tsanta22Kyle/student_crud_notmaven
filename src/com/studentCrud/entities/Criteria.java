package com.studentCrud.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.junit.jupiter.api.Order;

import java.sql.Timestamp;
@AllArgsConstructor@EqualsAndHashCode@Getter
public class Criteria {
    private String criteriaName;//order
    private Object value;//ASC
    private String operator;




}
