package com.edgarluque.m6.uf2.activitat1_2;

import java.sql.*;
import java.util.*;

public class Client {
    private int dni;
    private String nom;
    private boolean premium;

    public Client(int dni, String nom, boolean premium) {
        this.dni = dni;
        this.nom = nom;
        this.premium = premium;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return dni == client.dni;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client{");
        sb.append("dni=").append(dni);
        sb.append(", nom='").append(nom).append('\'');
        sb.append(", premium=").append(premium);
        sb.append('}');
        return sb.toString();
    }

    public static void createTable(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(
                    "CREATE TABLE Client (" +
                            "dni INT PRIMARY KEY," +
                            "nom VARCHAR(50) NOT NULL," +
                            "premium BOOLEAN" +
                            ");");
            System.out.println("Taula Client creada.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insert(Connection conn) {
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO Client (dni, nom, premium) VALUES (?,?,?);")) {
            stmt.setInt(1, dni);
            stmt.setString(2, nom);
            stmt.setBoolean(3, premium);
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashSet<Comanda> getComandes(Connection conn) {
        return Comanda.queryByClientID(conn, dni);
    }

    public static Client fromInput() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Introdueix el dni:");
        int dni = sc.nextInt();
        System.out.println("Introdueix el nom:");
        String nom = sc.next();
        System.out.println("Introdueix si es premium (s/n):");
        boolean premium = sc.next().equalsIgnoreCase("s");

        return new Client(dni, nom, premium);
    }

    public static HashSet<Client> query(Connection conn) {
        HashSet<Client> clients = new HashSet<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(
                    "SELECT * FROM Client;"
            );
            while (resultSet.next()) {
                int dni = resultSet.getInt("dni");
                String nom = resultSet.getString("nom");
                boolean premium = resultSet.getBoolean("premium");
                clients.add(new Client(dni, nom, premium));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return clients;
    }
}
