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
        MenuBuilder menu = new MenuBuilder();
        menu.addTitle("Activitat 3.2", true);
        menu.addSeparator();
        menu.addLine("1 - Eliminar un client", false);
        menu.addLine("2 - Modificar un client", false);
        menu.addLine("3 - Cerca un client per nom", false);
        menu.addLine("4 - Alta d'un nou client", false);
        menu.addLine("5 - Alta d'una nova comanda", false);
        menu.addLine("6 - Mostrar comandes d'un client", false);
        menu.addSeparator();
        menu.addLine("0 - Sortir", false);
        menu.addSeparator();

        while (menu.isRunning()) {
            menu.print();

            switch (menu.askOption()) {
                case 1:
                    menu1(factory);
                    break;
                case 2:
                    menu2(factory);
                    break;
                case 3:
                    menu3(factory);
                    break;
                case 4:
                    menu4(factory);
                    break;
                case 5:
                    menu5(factory);
                    break;
                case 6:
                    menu6(factory);
                    break;
                case 0:
                    menu.requestStop();
                    break;
            }
        }

        factory.close();
        StandardServiceRegistryBuilder.destroy(serviceRegistry);
    }

    // Helper methods

    public static void printClients(List<Client> clients) {
        int i = 1;
        for (Client client : clients) {
            System.out.println(i + " - " + client.getDni() + " : " + client.getNom());
            i++;
        }
    }

    public static List<Client> loadClients(SessionFactory factory) {
        try (Session session = factory.openSession()) {
            List<Client> clients = new ArrayList<>();
            Query<Client> query = session.createQuery("FROM Client", Client.class);
            clients = query.list();
            return clients;
        }
    }

    public static Client selectClient(SessionFactory factory) {
        List<Client> clients = loadClients(factory);

        System.out.println("Selecciona un client:");
        printClients(clients);

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        if (n < 1 || n > clients.size()) {
            System.out.println("El client seleccionat es invalid.");
            return null;
        }
        return clients.get(n-1);
    }

    public static boolean existeixComanda(SessionFactory factory, int numComanda) {
        Session session = factory.openSession();
        Query<Comanda> query = session.createQuery("FROM Comanda where num_comanda = :num LIMIT 1", Comanda.class);
        boolean exists = !query.setParameter("num", numComanda).list().isEmpty();
        session.close();
        return exists;
    }

    // Menus

    public static void menu1(SessionFactory factory) {
        Client client = selectClient(factory);

        if (client == null) {
            System.out.println("Client no trobat.");
            return;
        }

        try (Session session = factory.openSession()) {
            // Les comandes es borren per el CASCADE delete.
            session.beginTransaction();
            session.delete(client);
            session.getTransaction().commit();
        }
    }

    public static void menu2(SessionFactory factory) {
        Client client = selectClient(factory);

        if (client == null) {
            System.out.println("Client no trobat.");
            return;
        }

        try (Session session = factory.openSession()) {
            Scanner sc = new Scanner(System.in);

            System.out.println("Introdueix el nou nom:");
            String nom = sc.nextLine();
            System.out.println("Es premium (s/n)?:");
            boolean premium = sc.nextLine().equals("s");
            client.setNom(nom);
            client.setPremium(premium);

            session.beginTransaction();
            session.update(client);
            session.getTransaction().commit();
        }
    }

    public static void menu3(SessionFactory factory) {
        try (Session session = factory.openSession()) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Introdueix el nom:");
            String nom = sc.nextLine();


            Query<Client> query = session.createQuery("FROM Client where nom LIKE :name", Client.class);
            List<Client> clients = query.setParameter("name", nom + "%").list();
            if (clients.isEmpty()) {
                System.out.println("No s'ha trobat cap client.");
            } else
                printClients(clients);
        }
    }

    public static void menu4(SessionFactory factory) {
        try (Session session = factory.openSession()) {
            System.out.println("Creacio d'un client.");
            List<Client> clients;
            do {
                System.out.println("Introdeix les dades del client.");
                Client c = Client.fromInput();
                // Mira si existeix.
                Query<Client> query = session.createQuery("FROM Client where dni = :dni", Client.class);
                clients = query.setParameter("dni", c.getDni()).list();
                if (!clients.isEmpty()) {
                    System.out.println("Ja existeix un client amb aquest dni.");
                } else {
                    session.beginTransaction();
                    session.save(c);
                    session.getTransaction().commit();
                    System.out.println("Client guardat.");
                }
            } while (!clients.isEmpty());
        }
    }

    public static void menu5(SessionFactory factory) {
        Client client = selectClient(factory);

        if (client == null) {
            System.out.println("Client no trobat.");
            return;
        }

        try (Session session = factory.openSession()) {
            System.out.println("Creacio d'una comanda.");
            List<Comanda> comandas;

            do {
                System.out.println("Introdeix les dades de la comanda.");
                Comanda comanda = Comanda.fromInput(client);
                // Mira si existeix.
                Query<Comanda> query = session.createQuery("FROM Comanda where num_comanda = :num", Comanda.class);
                comandas = query.setParameter("num", comanda.getNumComanda()).list();
                if (!comandas.isEmpty()) {
                    System.out.println("Ja existeix una comanda amb aquest numero.");
                } else {
                    session.beginTransaction();
                    session.save(comanda);
                    session.getTransaction().commit();
                    System.out.println("Comanda guardada.");
                }
            } while (!comandas.isEmpty());
        }
    }

    public static void menu6(SessionFactory factory) {
        Client client = selectClient(factory);

        if (client == null) {
            System.out.println("Client no trobat.");
            return;
        }

        try (Session session = factory.openSession()) {
            Query<Comanda> query = session.createQuery("FROM Comanda where dni_client = :dni", Comanda.class);
            List<Comanda> comandas = query.setParameter("dni", client.getDni()).list();

            for (Comanda comanda : comandas) {
                System.out.println(comanda);
                System.out.println("--------");
            }
        }
    }
}
