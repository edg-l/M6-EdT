package com.edgarluque.m6.uf2.activitat2_2;

import com.edgarluque.m6.util.MenuBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

public class Program {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        MenuBuilder menu = new MenuBuilder();
        menu.addTitle("Activitat 1.2 UF2", true);
        menu.addSeparator();
        menu.addLine("0. Sortir", false);
        menu.addLine("1. Crear models", false);
        menu.addLine("2. Recuperacio de les dades de la BBDD", false);
        menu.addLine("3. Emmagatzemat de dades a la BBDD", false);
        menu.addLine("4. Alta d'un nou client", false);
        menu.addLine("5. Alta d'una nova comanda", false);
        menu.addLine("6. Mostrar per pantalla les comandes d'un client", false);
        menu.addLine("7. Generació de resum de facturació", false);

        SortedSet<Client> clients = new TreeSet<>();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/m6", "m6", "m6")) {
            conn.setAutoCommit(false);
            while (menu.isRunning()) {
                menu.print();

                switch (menu.askOption()) {
                    case 0:
                        menu.requestStop();
                        break;
                    case 1:
                        Client.createTable(conn);
                        Comanda.createTable(conn);
                        menu.waitForInput();
                        break;
                    case 2:
                        clients = Client.query(conn);
                        menu.waitForInput();
                        break;
                    case 3:
                        Client.deleteAll(conn);
                        for (Client client : clients)
                            client.insert(conn);
                        System.out.println("Borrades totes les dades de la BBDD i insertat les dades en memoria.");
                        break;
                    case 4:
                        if (clients.add(Client.fromInput())) {
                            System.out.println("Client afegit.");
                        } else {
                            System.out.println("El client ja existeix.");
                        }

                        break;
                    case 5: {
                        System.out.println("Aquests son els clients");
                        printLlistaClients(clients.iterator());

                        int opcio = opcioRang(1, clients.size());

                        Client client = findClientByIndex(clients.iterator(), opcio);

                        if(client == null) {
                            System.out.println("El client es null, no hauria de pasar");
                            break;
                        }

                        client.getComandes().add(Comanda.fromInput(client.getDni()));
                        System.out.println("Comande afegadira al client amb dni " + client.getDni());
                        break;
                    }
                    case 6: {
                        System.out.println("Aquests son els clients");
                        printLlistaClients(clients.iterator());
                        int opcio = opcioRang(1, clients.size());
                        Client client = findClientByIndex(clients.iterator(), opcio);

                        if(client == null) {
                            System.out.println("El client es null, no hauria de pasar");
                            break;
                        }

                        System.out.println("---");
                        for(Comanda comanda: client.getComandes()) {
                            System.out.println(comanda);
                            System.out.println("---");
                        }
                        break;
                    }
                    case 7: {
                        ResumFacturacio.generateFacturacio(conn);
                        break;
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void printLlistaClients(Iterator<Client> it) {
        int i = 1;
        while (it.hasNext()) {
            Client client = it.next();
            System.out.printf("%d) %s - %s", i + 1, client.getDni(), client.getNom());
            i++;
        }
    }

    private static Client findClientByIndex(Iterator<Client> it, int index) {
        int i = 1;
        while (it.hasNext()) {
            Client client = it.next();
            if(i == index) {
                return client;
            }
            i++;
        }
        return null;
    }

    private static int opcioRang(int min, int max) {
        int opcio = 0;
        Scanner sc = new Scanner(System.in);
        while (opcio < min || opcio > max) {
            System.out.println("Opcio:");
            opcio = sc.nextInt();
        }
        return opcio;
    }
}