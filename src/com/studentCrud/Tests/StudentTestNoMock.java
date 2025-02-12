package com.studentCrud.Tests;

import com.studentCrud.DAO.studentDAO;
import com.studentCrud.entities.Criteria;
import com.studentCrud.entities.OrderBy;
import com.studentCrud.entities.Student;
import com.studentCrud.entities.order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class StudentTestNoMock {

    @Test
    public void getOrderFilterByCriteria() throws SQLException {

        List <Student> studentsExpected = new ArrayList<>();
        studentsExpected.add(new Student("F", Timestamp.valueOf("2004-11-21 00:00:00.0"),"gr-J1","roxanne","deBruyne","STD0003","f02f0b0d-98ea-4cf0-88ed-66cc77e46809"));
        studentsExpected.add(new Student("F", Timestamp.valueOf("2004-11-21 00:00:00.0"),"gr-J1","roxanne","deBruyne","STD0002","68bdf552-9f3b-4105-a830-92c47945aca4"));

        studentDAO subject = new studentDAO();
        List<Criteria> criteriaList = new ArrayList<>();
        criteriaList.add(new Criteria("lastname", "de","LIKE"));
        //criteriaList.add(new Criteria("ORDER", "ASC","BY"));
        List<Student> actual = subject.findByCriteria(criteriaList,1,2,new OrderBy("firstname", order.DESC));
       Assertions.assertEquals(studentsExpected.toString(),actual.toString());

       // System.out.println(actual.toString());
    }

}
