package com.edgarluque.m6.uf2.activitat3_2;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class Program {
    public static void main(String[] args) {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure().build();
        SessionFactory factory = new MetadataSources(serviceRegistry)
                .buildMetadata().buildSessionFactory();



        factory.close();
        StandardServiceRegistryBuilder.destroy(serviceRegistry);
    }
}
