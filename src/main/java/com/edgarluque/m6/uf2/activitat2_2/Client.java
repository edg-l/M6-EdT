package com.edgarluque.m6.uf2.activitat2_2;

import java.sql.*;
import java.util.*;

public class Client implements Comparable<Client> {
    private int dni;
    private String nom;
    private boolean premium;
    private HashSet<Comanda> comandes;

    public Client(int dni, String nom, boolean premium) {
        this.dni = dni;
        this.nom = nom;
        this.premium = premium;
        this.comandes = new HashSet<>();
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

    @Override
    public int compareTo(Client client) {
        return Integer.compare(dni, client.dni);
    }

    public static void createTable(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(
                    "CREATE TABLE Client (" +
                            "dni INT PRIMARY KEY," +
                            "nom VARCHAR(50) NOT NULL," +
                            "premium BOOLEAN" +
                            ");");
            conn.commit();
            System.out.println("Taula Client creada.");
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());;
        }
    }

    public static void deleteAll(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM Clients;");
            System.out.println("Totes les dades eliminades.");
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());;
        }
    }

    public void insert(Connection conn) {
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO Client (dni, nom, premium) VALUES (?,?,?);")) {
            stmt.setInt(1, dni);
            stmt.setString(2, nom);
            stmt.setBoolean(3, premium);
            stmt.execute();
            conn.commit();

            for(Comanda cmd: comandes) {
                cmd.insert(conn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadComandes(Connection conn) {
        comandes = Comanda.queryByClientID(conn, dni);
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

    public static SortedSet<Client> query(Connection conn) {
        SortedSet<Client> clients = new TreeSet<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(
                    "SELECT * FROM Client;"
            );
            conn.commit();
            while (resultSet.next()) {
                int dni = resultSet.getInt("dni");
                String nom = resultSet.getString("nom");
                boolean premium = resultSet.getBoolean("premium");
                Client client = new Client(dni, nom, premium);
                client.loadComandes(conn);
                clients.add(client);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return clients;
    }

    public int getDni() {
        return dni;
    }

    public String getNom() {
        return nom;
    }

    public HashSet<Comanda> getComandes() {
        return comandes;
    }
}
