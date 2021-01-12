package com.edgarluque.m6.uf2.activitat3_1;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Date;

public class Program {
    public static void main(String[] args) {
        Persona p = new Persona(1, new Date());
        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");
        config.addAnnotatedClass(Persona.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {

            SessionFactory factory = new MetadataSources(serviceRegistry)
                    .buildMetadata().buildSessionFactory();
            Session session = factory.openSession();
            session.beginTransaction();
            session.save(p);
            session.getTransaction().commit();
            session.close();
            factory.close();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
        }
    }
}
