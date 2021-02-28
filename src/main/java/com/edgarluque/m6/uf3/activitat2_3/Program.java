package com.edgarluque.m6.uf3.activitat2_3;

import com.edgarluque.m6.util.MenuBuilder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.print.Doc;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        try (MongoClient client = MongoClients.create()) {
            MongoDatabase db = client.getDatabase("bd_comandes");
            MongoCollection<Document> collection = db.getCollection("clients");

            exercisi7(collection);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void exercisi1(MongoCollection<Document> collection) {
        Client client = new Client("12345678X", "Pere Pons", 0);
        collection.insertOne(client.toDocument());
    }

    public static void exercisi2(MongoCollection<Document> collection) {
        Scanner sc = new Scanner(System.in);


        String nif = askNonBlank(sc, "Introdueix el nif: ");
        String nom = askNonBlank(sc, "Introdueix el nom: ");

        System.out.print("Introdueix la factoracio total: ");
        int facturacio = sc.nextInt();

        Client client = new Client(nif, nom, facturacio);

        sc.nextLine();

        System.out.print("Introdueix el telefon (opcional): ");
        String telefon = sc.nextLine();

        if(!telefon.isBlank())
            client.setTelefon(telefon);

        System.out.print("Introdueix el correu (opcional): ");
        String correu = sc.nextLine();

        if(!correu.isBlank())
            client.setCorreu(correu);

        collection.insertOne(client.toDocument());
    }

    public static void exercisi3(MongoCollection<Document> collection) {
        List<Client> clients = Client.fromIterable(collection.find());

        for(Client c: clients) {
            System.out.println(c.getNif());
        }
    }

    public static void exercisi4(MongoCollection<Document> collection) throws ParseException {
        List<Client> clients = Client.fromIterable(collection.find());

        System.out.println("Selecciona un client:");
        int i = 0;
        for(Client c: clients) {
            System.out.println(i + ") " + c.getNif());
            i++;
        }

        Scanner sc = new Scanner(System.in);

        int opcio = sc.nextInt();
        sc.nextLine();

        if(opcio < 0 || opcio >= clients.size()) {
            System.out.println("Client invalid.");
            return;
        }

        Client client = clients.get(opcio);

        System.out.println("Data (dd-MM-yyyy): ");

        String data = sc.nextLine();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = format.parse(data);

        System.out.println("Import:");
        int importe = sc.nextInt();
        sc.nextLine();

        Comanda comanda = new Comanda(date, importe, true);

        collection.deleteOne(client.toDocument());

        client.setTotalFacturacio(client.getTotalFacturacio() + comanda.getImporte());
        client.getComandes().add(comanda);

        collection.insertOne(client.toDocument());
    }

    public static void exercisi5(MongoCollection<Document> collection) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Facturació:");
        int fact = sc.nextInt();
        sc.nextLine();

        List<Client> clients = Client.fromIterable(
                collection.find(
                        new Document("totalFacturacio",
                                new Document("$gt", fact)
                        )
                )
        );

        for(Client client:clients)
            System.out.println(client);
    }

    public static void exercisi6(MongoCollection<Document> collection) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Quantitat de comandes:");
        int quant = sc.nextInt();
        sc.nextLine();

        List<Client> clients = Client.fromIterable(
                collection.find(
                        new Document("$where", "this.comandes.length > " + quant)
                                .append("comandes", new Document("$exists", true))
                )
        );

        for(Client client:clients)
            System.out.println(client);
    }

    public static void exercisi7(MongoCollection<Document> collection) throws ParseException {
        MenuBuilder menu = new MenuBuilder();
        menu.addTitle("Menu", true);
        menu.addLine("0) Sortir", false);
        menu.addLine("1) Donar d'alta un client", false);
        menu.addLine("2) Afegir comandes a un client", false);
        menu.addLine("3) Cercar clients per facturació", false);
        menu.addLine("4) Cercar clients per quantitat de comandes", false);
        menu.addSeparator();

        while(menu.isRunning()) {
            menu.print();

            switch (menu.askOption()) {
                case 0:
                    menu.requestStop();
                    break;
                case 1:
                    exercisi2(collection);
                    break;
                case 2:
                    exercisi4(collection);
                    break;
                case 3:
                    exercisi5(collection);
                    break;
                case 4:
                    exercisi6(collection);
                    break;
            }
        }
    }

    public static String askNonBlank(Scanner sc, String question) {
        String v;

        do {
            System.out.print(question);
            v = sc.nextLine();
        } while (v.isBlank());

        return v;
    }
}
