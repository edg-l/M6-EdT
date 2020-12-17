package com.edgarluque.m6.uf2.activitat2_2;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;

public class Comanda {
    private int numComanda;
    private int preuTotal;
    private Date data;
    private int dniClient;

    public Comanda(int numComanda, int preuTotal, Date data, int dniClient) {
        this.numComanda = numComanda;
        this.preuTotal = preuTotal;
        this.data = data;
        this.dniClient = dniClient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comanda comanda = (Comanda) o;
        return numComanda == comanda.numComanda;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numComanda);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Comanda{");
        sb.append("numComanda=").append(numComanda);
        sb.append(", preuTotal=").append(preuTotal);
        sb.append(", data=").append(data);
        sb.append(", dniClient=").append(dniClient);
        sb.append('}');
        return sb.toString();
    }

    public void insert(Connection conn) {
        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO Comandes (num_comanda, preu_total, data, dni_client) VALUES (?,?,?,?);"
        )) {
            stmt.setInt(1, numComanda);
            stmt.setInt(2, preuTotal);
            stmt.setDate(3, java.sql.Date.valueOf(data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
            stmt.setInt(4, dniClient);
            stmt.execute();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Comanda fromInput(int dniClient) {
        Scanner sc = new Scanner(System.in);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println("Introdueix el numero de la comanda:");
        int numComanda = sc.nextInt();
        System.out.println("Preu total:");
        int preuTotal = sc.nextInt();
        Date data;
        while (true) {
            try {
                System.out.println("Introdueix la data (yyyy-MM-dd)");
                data = formatter.parse(sc.nextLine());
                break;
            } catch (ParseException ignored) {
            }
        }

        return new Comanda(numComanda, preuTotal, data, dniClient);
    }

    public static void createTable(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(
                    "CREATE TABLE Comanda (" +
                            "num_comanda INT PRIMARY KEY," +
                            "preu_total DECIMAL(11, 2) NOT NULL," +
                            "data DATE NOT NULL," +
                            "dni_client INT," +
                            "FOREIGN KEY (dni_client) REFERENCES Client(dni)" +
                            ");");
            conn.commit();
            System.out.println("Taula Comanda creada.");
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());;
        }
    }

    public static Comanda fromResultSet(ResultSet resultSet) throws SQLException {
        int numComanda = resultSet.getInt("num_comanda");
        int preuTotal = resultSet.getInt("preu_total");
        Date data = resultSet.getDate("data");
        int dniClient = resultSet.getInt("dni_client");

        return new Comanda(numComanda, preuTotal, data, dniClient);
    }

    public static HashSet<Comanda> query(Connection conn) {
        HashSet<Comanda> comandes = new HashSet<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(
                    "SELECT * FROM Comanda;"
            );
            conn.commit();
            while (resultSet.next()) {
                comandes.add(Comanda.fromResultSet(resultSet));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return comandes;
    }

    public static HashSet<Comanda> queryByClientID(Connection conn, int dniClient) {
        HashSet<Comanda> comandes = new HashSet<>();

        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM Comanda WHERE dni_client = ?;"
        )) {
            stmt.setInt(1, dniClient);
            ResultSet resultSet = stmt.executeQuery();
            conn.commit();
            while (resultSet.next()) {
                comandes.add(Comanda.fromResultSet(resultSet));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return comandes;
    }
}
