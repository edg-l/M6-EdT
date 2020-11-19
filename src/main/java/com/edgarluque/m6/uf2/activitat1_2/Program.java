package com.edgarluque.m6.uf2.activitat1_2;

import com.edgarluque.m6.util.MenuBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;

public class Program {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        MenuBuilder menu = new MenuBuilder();
        menu.addTitle("Activitat 1.2 UF2", true);
        menu.addSeparator();
        menu.addLine("0. Sortir", false);
        menu.addLine("1. Crear models", false);
        menu.addLine("2. Recuperacio de les dades de la BBDD", false);
        menu.addLine("3. Emmagatzemat de dades a la BBDD", false);
        menu.addLine("4. Alta d'un nou client", false);
        menu.addLine("5. Alta d'una nova comanda", false);

        HashSet<Client> clients = new HashSet<>();

        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/m6", "m6", "m6")) {
            while (menu.isRunning()) {
                menu.print();

                switch (menu.askOption()) {
                    case 0:
                        menu.requestStop();
                        break;
                    case 1:
                        Client.createTable(conn);
                        Comanda.createTable(conn);
                        menu.waitForInput();
                        break;
                    case 2:
                        clients = Client.query(conn);
                        menu.waitForInput();
                        break;
                    case 3:
                        Client.deleteAll(conn);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}