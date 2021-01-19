package com.edgarluque.m6.uf2.activitat3_2;

import com.edgarluque.m6.uf2.activitat3_1.Politic;
import com.edgarluque.m6.util.MenuBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure().build();
        SessionFactory factory = new MetadataSources(serviceRegistry)
                .buildMetadata().buildSessionFactory();

        // codi aqui
        List<Client> clients = loadClients(factory);
        MenuBuilder menu = new MenuBuilder();
        menu.addTitle("Activitat 3.2", true);
        menu.addSeparator();
        menu.addLine("1 - Eliminar un client", false);

        factory.close();
        StandardServiceRegistryBuilder.destroy(serviceRegistry);
    }

    public static void printClients(List<Client> clients) {
        int i = 1;
        for(Client client: clients) {
            System.out.println(i + " - " + client);
            i++;
        }
    }

    public static List<Client> loadClients(SessionFactory factory) {
        Session session = factory.openSession();

        List<Client> clients = new ArrayList<>();
        try {
            Query<Client> query = session.createQuery("FROM Client", Client.class);
            clients = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return clients;
    }

    public static void menu1(SessionFactory factory) {
        Scanner sc = new Scanner(System.in);
        System.out.println("DNI del client:");
        int dni = sc.nextInt();

        Session session = factory.openSession();
        session.beginTransaction();

        try {
            Client client = session.get(Client.class, dni);
            if(client != null) {
                session.delete(client);
                session.getTransaction().commit();
            } else {
                System.out.println("El client no existeix.");
                session.getTransaction().rollback();
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
