package com.edgarluque.m6.uf2.activitat1_1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Program {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/m6", "m6", "m6")) {
            Politic.createTable(conn);

            Politic.fromInput().insert(conn);
        }
    }
}