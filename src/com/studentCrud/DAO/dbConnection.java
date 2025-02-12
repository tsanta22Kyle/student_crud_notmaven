package com.studentCrud.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {


    public dbConnection() {
       /* final String user = System.getenv("USER");
        final String host = System.getenv("HOST");
        final String password = System.getenv("PASSWORD");
        final String database = System.getenv("DBName");
        String dbUrl = "jdbc:postgresql://" + host + ":5432/" + database;
        try {

            conn = DriverManager.getConnection(dbUrl, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
    }

    public Connection getConnection() {

        final String user = System.getenv("USER");
        final String host = System.getenv("HOST");
        final String password = System.getenv("PASSWORD");
        final String database = System.getenv("DBName");
        String dbUrl = "jdbc:postgresql://" + host + ":5432/" + database;
        try {

            return DriverManager.getConnection(dbUrl, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
