package com.edgarluque.m6.examen_uf2;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Program {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        List<Monstre> monstres = obtenirMonstresJDBC(3);
        for(Monstre m: monstres) {
            System.out.println(m.getNom());
        }
    }

    public static void guardarNivellJDBC(Nivell nivell) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/m6", "m6", "m6")) {
            try(PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO NIVELL (id_nivell, nom, dificultat) VALUES (?,?,?)"
            )) {
                stmt.setInt(1, nivell.getCodi());
                stmt.setString(2, nivell.getNom());
                stmt.setInt(3, nivell.getDificultat());
                stmt.execute();
            }

            // Inserta tot els monstres
            for(Monstre monstre: nivell.getMonstres()) {
                try(PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO MONSTRE (id_monstre, nom, atac, id_nivell) VALUES (?,?,?,?)"
                )) {
                    stmt.setInt(1, monstre.getCodi());
                    stmt.setString(2, monstre.getNom());
                    stmt.setInt(3, monstre.getAtac());
                    stmt.setInt(4, nivell.getCodi());
                    stmt.execute();
                }
            }
        }
    }

    public static List<Monstre> obtenirMonstresJDBC(int atac_min) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        List<Monstre> monstres = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/m6", "m6", "m6")) {
            try(PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM MONSTRE WHERE atac >= ?"
            )) {
                stmt.setInt(1, atac_min);
                ResultSet resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    monstres.add(new Monstre(
                            resultSet.getInt("id_monstre"),
                            resultSet.getString("nom"),
                            resultSet.getInt("atac")
                    ));
                }
            }
        }

        return monstres;
    }

    public static void guardarNivell(Nivell nivell) {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure().build();
        SessionFactory factory = new MetadataSources(serviceRegistry)
                .buildMetadata().buildSessionFactory();

        try(Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(nivell);
            session.getTransaction().commit();
        }

        factory.close();
        StandardServiceRegistryBuilder.destroy(serviceRegistry);
    }

    public static List<Nivell> obtenirNivells(int dificultat_max) {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure().build();
        SessionFactory factory = new MetadataSources(serviceRegistry)
                .buildMetadata().buildSessionFactory();

        List<Nivell> nivells = new ArrayList<>();

        try(Session session = factory.openSession()) {
            Query<Nivell> query = session.createQuery("from Nivell where dificultat <= :dif", Nivell.class);
            query.setParameter("dif", dificultat_max);
            nivells = query.list();
        }

        factory.close();
        StandardServiceRegistryBuilder.destroy(serviceRegistry);

        return nivells;
    }
}
