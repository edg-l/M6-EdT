package com.edgarluque.m6.uf2.activitat3_1;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure().build();
        SessionFactory factory = new MetadataSources(serviceRegistry)
                .buildMetadata().buildSessionFactory();
        try {
            System.out.println("a:");
            List<Politic> politics = loadPolitics(factory);
            System.out.println("politics:");
            System.out.println(politics);
            System.out.println("length: " + politics.size());
            for(Politic p: politics) {
                System.out.println(p);
            }
            factory.close();
        } catch (Exception e) {
            System.out.println("excepcio:");
            System.out.println(e);
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
        }
    }

    public static void savePolitic(SessionFactory factory, Politic politic) {
        Session session = factory.openSession();
        session.beginTransaction();
        session.save(politic);
        session.getTransaction().commit();
        session.close();
    }

    public static List<Politic> loadPolitics(SessionFactory factory) {
        Session session = factory.openSession();
        Query<Politic> query = session.createQuery("FROM Politic", Politic.class);
        List<Politic> politics = query.list();
        session.close();
        return politics;
    }
}
