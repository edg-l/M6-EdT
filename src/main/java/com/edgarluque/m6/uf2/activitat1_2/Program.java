package com.edgarluque.m6.uf2.activitat1_2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Program {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/m6", "m6", "m6")) {

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
