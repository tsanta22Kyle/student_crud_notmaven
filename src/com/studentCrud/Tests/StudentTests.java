package com.studentCrud.Tests;

import com.studentCrud.DAO.dbConnection;
import com.studentCrud.DAO.*;
import com.studentCrud.entities.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



public class StudentTests {

    @BeforeEach
     public void initializeMockito(){
        MockitoAnnotations.initMocks(this);
    }

    dbConnection dbConnectionMock = mock(dbConnection.class);
    studentDAO subject = new studentDAO(dbConnectionMock);

    @Test
    public void getAllStudentsTest() throws SQLException {
        Connection connectionMock = mock(Connection.class);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        ResultSet resultSetMock = mock(ResultSet.class);

        Student expectedStudent = new Student();
        String studentId = "an-normal-id";
        String studentFirstName = "john";
        String studentLastName = "doe";
        Timestamp birthDate = Timestamp.valueOf(LocalDateTime.now());
        String sex = "M";
        expectedStudent.setId(studentId);
        expectedStudent.setFirstname(studentFirstName);
        expectedStudent.setLastname(studentLastName);
        expectedStudent.setBirthdate(birthDate);



        when(resultSetMock.next()).thenReturn(true).thenReturn(false);
        when(resultSetMock.getString(1)).thenReturn(studentId);
        when(resultSetMock.getTimestamp(3)).thenReturn(birthDate);
        when(resultSetMock.getString("lastname")).thenReturn(studentLastName);
        when(resultSetMock.getString("firstname")).thenReturn(studentFirstName);
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        preparedStatementMock.setInt(1,1);
        preparedStatementMock.setInt(2,5);
        when(connectionMock.prepareStatement(eq("SELECT * FROM student limit ? offset ?"))).thenReturn(preparedStatementMock);
       when(dbConnectionMock.getConnection()).thenReturn(connectionMock);

        List<Student> actual = subject.getAll(1,5);

        List<Student> expected = new ArrayList<>();

        expected.add(expectedStudent);

        Assertions.assertEquals(List.of(expectedStudent),
               actual);
        //System.out.println("expected : " + expected);
      //  System.out.println("actual : " + actual);
        //  when(studentDAO.getAll()).thenReturn(new ArrayList<>());
    }

    @Test
    public void filterStudentsByName() throws SQLException{
        PreparedStatement statementMock = mock(PreparedStatement.class);
        ResultSet resultSetMock = mock(ResultSet.class);
        Connection connectionMock = mock(Connection.class);

        // expected data
        Student expectedStudent = new Student();
        String student_id = "another_cool_id";
        String firstname = "kyle";
        String lastname = "RAKOTOARISON";
        Timestamp birthdate = Timestamp.valueOf("2006-07-21 00:00:00");
        String sex = "M";
        String reference = "STD23084";
        String group_id = "J1";

        expectedStudent.setBirthdate(birthdate);
        expectedStudent.setLastname(lastname);
        expectedStudent.setFirstname(firstname);
        expectedStudent.setId(student_id);
        expectedStudent.setGroup(group_id);
        expectedStudent.setReference(reference);
        expectedStudent.setSex(sex);

        //process

        when(resultSetMock.next()).thenReturn(true).thenReturn(false);
        when(resultSetMock.getString(1)).thenReturn(student_id);
        when(resultSetMock.getString(2)).thenReturn(sex);
        when(resultSetMock.getTimestamp(3)).thenReturn(birthdate);
        when(resultSetMock.getString(4)).thenReturn(reference);
        when(resultSetMock.getString(5)).thenReturn(group_id);
        when(resultSetMock.getString(6)).thenReturn(firstname);
        when(resultSetMock.getString(7)).thenReturn(lastname);


        when(statementMock.executeQuery()).thenReturn(resultSetMock);
        statementMock.setString(1,"k");
        statementMock.setString(2,"RA");
        statementMock.setInt(3,5);
        statementMock.setInt(4,1);

        when(connectionMock.prepareStatement("SELECT * FROM student WHERE firstname ILIKE ? AND lastname ILIKE ? ORDER BY firstname ASC limit ? offset ?")).thenReturn(statementMock);
        when(dbConnectionMock.getConnection()).thenReturn(connectionMock);

        //test
        List<Student> actual = subject.filterByName(5,1,"k","RA");

        Assertions.assertEquals(List.of(expectedStudent),actual);


    }

    @Test
    public void filterStudentByBirthdate() throws SQLException{
        Connection connectionMock = mock(Connection.class);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        ResultSet resultSetMock = mock(ResultSet.class);

        Student expectedStudent = new Student();
        String student_id = "another_cool_id";
        String firstname = "kyle";
        String lastname = "RAKOTOARISON";
        Timestamp birthdate = Timestamp.valueOf("2006-07-21 00:00:00");
        String sex = "M";
        String reference = "STD23084";
        String group_id = "J1";

        when(dbConnectionMock.getConnection()).thenReturn(connectionMock);
        when(connectionMock.prepareStatement(" SELECT * FROM  student WHERE birthdate>= ? AND birthdate<? ORDER BY firstname ASC limit ? offset ?")).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true).thenReturn(false);
        when(resultSetMock.getString(1)).thenReturn(student_id);
        when(resultSetMock.getString(2)).thenReturn(sex);
        when(resultSetMock.getTimestamp(3)).thenReturn(birthdate);
        when(resultSetMock.getString(4)).thenReturn(reference);
        when(resultSetMock.getString(5)).thenReturn(group_id);
        when(resultSetMock.getString(6)).thenReturn(firstname);
        when(resultSetMock.getString(7)).thenReturn(lastname);

        expectedStudent.setBirthdate(birthdate);
        expectedStudent.setLastname(lastname);
        expectedStudent.setFirstname(firstname);
        expectedStudent.setId(student_id);
        expectedStudent.setGroup(group_id);
        expectedStudent.setReference(reference);
        expectedStudent.setSex(sex);

        preparedStatementMock.setString(1,"k");
        preparedStatementMock.setString(2,"RA");
        preparedStatementMock.setInt(3,5);
        preparedStatementMock.setInt(4,1);

        Timestamp debut = Timestamp.valueOf("2002-07-21 00:00:00");
        Timestamp end = Timestamp.valueOf("2010-07-21 00:00:00");

        List<Student> actual = subject.filterByBirthDate(1,3,debut,end);

        Assertions.assertEquals(List.of(expectedStudent),actual);


    }

    @Test
    public void orderByName() throws SQLException{
        Connection connectionMock = mock(Connection.class);
        PreparedStatement statementMock = mock(PreparedStatement.class);
        ResultSet resultSetMock = mock(ResultSet.class);

        String firstname = "sung";
        String lastname = "jin woo";
        String student_id = "rank-S-jw";
        String ref = "STD?????";
        String group_id = "gr-hunter";
        Timestamp birthdate = Timestamp.valueOf("2000-02-06 00:00:00");
        String sex = "M";
        Student expectedStudent = new Student(sex,birthdate,group_id,firstname,lastname,ref,student_id);

        when(resultSetMock.next()).thenReturn(true).thenReturn(false);
        when(resultSetMock.getString(1)).thenReturn(student_id);
        when(resultSetMock.getString(2)).thenReturn(sex);
        when(resultSetMock.getTimestamp(3)).thenReturn(birthdate);
        when(resultSetMock.getString(4)).thenReturn(ref);
        when(resultSetMock.getString(5)).thenReturn(group_id);
        when(resultSetMock.getString(6)).thenReturn(firstname);
        when(resultSetMock.getString(7)).thenReturn(lastname);



        when(statementMock.executeQuery()).thenReturn(resultSetMock);
        statementMock.setInt(1,1);
        statementMock.setInt(2,1);
        when(connectionMock.prepareStatement(" select * from student order by firstname ,lastname ASC offset ? limit ?")).thenReturn(statementMock);

        when(dbConnectionMock.getConnection()).thenReturn(connectionMock);

        List<Student> actual = subject.orderByName(1,5);

        Assertions.assertEquals(List.of(expectedStudent),actual);

    }

    @Test
    public void orderByBirthdate() throws SQLException{
        Connection connectionMock = mock(Connection.class);
        PreparedStatement statementMock = mock(PreparedStatement.class);
        ResultSet resultSetMock = mock(ResultSet.class);

        String firstname = "sung";
        String lastname = "jin woo";
        String student_id = "rank-S-jw";
        String ref = "STD?????";
        String group_id = "gr-hunter";
        Timestamp birthdate = Timestamp.valueOf("2000-02-06 00:00:00");
        String sex = "M";
        Student expectedStudent = new Student(sex,birthdate,group_id,firstname,lastname,ref,student_id);

        when(resultSetMock.next()).thenReturn(true).thenReturn(false);
        when(resultSetMock.getString(1)).thenReturn(student_id);
        when(resultSetMock.getString(2)).thenReturn(sex);
        when(resultSetMock.getTimestamp(3)).thenReturn(birthdate);
        when(resultSetMock.getString(4)).thenReturn(ref);
        when(resultSetMock.getString(5)).thenReturn(group_id);
        when(resultSetMock.getString(6)).thenReturn(firstname);
        when(resultSetMock.getString(7)).thenReturn(lastname);

        when(statementMock.executeQuery()).thenReturn(resultSetMock);
        when(connectionMock.prepareStatement("select * from student order by birthdate ASC offset ? limit ?")).thenReturn(statementMock);
        when(dbConnectionMock.getConnection()).thenReturn(connectionMock);

        List<Student> actual = subject.orderByBirthdate(1,5);

        Assertions.assertEquals(List.of(expectedStudent),actual);

    }

}
