package com.edgarluque.m6.uf2.activitat3_1;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure().build();
        SessionFactory factory = new MetadataSources(serviceRegistry)
                .buildMetadata().buildSessionFactory();

        List<Politic> politics = loadPolitics(factory);
        for (Politic p : politics) {
            System.out.println(p);
        }
        factory.close();
        StandardServiceRegistryBuilder.destroy(serviceRegistry);
    }

    public static void savePolitic(SessionFactory factory, Politic politic) {
        Session session = factory.openSession();
        session.beginTransaction();

        try {
            session.save(politic);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static List<Politic> loadPolitics(SessionFactory factory) {
        Session session = factory.openSession();

        List<Politic> politics = new ArrayList<>();
        try {
            Query<Politic> query = session.createQuery("FROM Politic", Politic.class);
            politics = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return politics;
    }
}
