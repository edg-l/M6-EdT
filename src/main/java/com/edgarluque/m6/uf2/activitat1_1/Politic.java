package com.edgarluque.m6.uf2.activitat1_1;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Politic {
    private String nif;
    private String nom;
    private Date dataNaixament;
    private int sou;
    private boolean esCorrupte;

    public Politic(String nif, String nom, Date dataNaixament, int sou, boolean esCorrupte) {
        this.nif = nif;
        this.nom = nom;
        this.dataNaixament = dataNaixament;
        this.sou = sou;
        this.esCorrupte = esCorrupte;
    }

    public void insert(Connection conn) {
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO Politic (nif, nom, dataNaixement, sou, esCorrupte) VALUES (?,?,?,?,?);")) {
            stmt.setString(1, nif);
            stmt.setString(2, nom);
            stmt.setDate(3, java.sql.Date.valueOf(dataNaixament.toInstant().atZone(ZoneId.of("Europe/Madrid")).toLocalDate()));
            stmt.setInt(4, sou);
            stmt.setBoolean(5, esCorrupte);
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTable(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS Politic (" +
                            "nif CHAR(9) PRIMARY KEY," +
                            "nom VARCHAR(50) NOT NULL," +
                            "dataNaixement DATE," +
                            "sou INT," +
                            "esCorrupte BOOLEAN" +
                            ");");
            System.out.println("Taula Politic creada.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static List<Politic> load(Connection conn) {
        List<Politic> politics = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(
                    "SELECT nif, nom, dataNaixement, sou, esCorrupte from Politic;"
            );
            while (resultSet.next()) {
                String nif = resultSet.getString("nif");
                String nom = resultSet.getString("nom");
                Date dataNaixament = resultSet.getDate("dataNaixement");
                int sou = resultSet.getInt("sou");
                boolean esCorrupte = resultSet.getBoolean("esCorrupte");
                politics.add(new Politic(nif, nom, dataNaixament, sou, esCorrupte));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return politics;
    }

    public static Politic fromInput() {
        Scanner scanner = new Scanner(System.in);

        String nif;
        String nom;
        Date dataNaixament = null;
        int sou;
        boolean esCorrupte;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println("Introdueix el nif:");
        nif = scanner.nextLine();
        System.out.println("Introdueix el nom:");
        nom = scanner.nextLine();
        while (true) {
            try {
                System.out.println("Introdueix la data de naixament:");
                dataNaixament = formatter.parse(scanner.nextLine());
            } catch (ParseException e) {
                continue;
            } finally {
                break;
            }
        }
        System.out.println("Introdueix el sou:");
        sou = scanner.nextInt();
        System.out.println("Introdueix si es corrupte (s/n):");
        esCorrupte = scanner.next().equals("s");

        return new Politic(nif, nom, dataNaixament, sou, esCorrupte);
    }
}
