package com.edgarluque.m6.uf3.activitat2_3;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.print.Doc;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        try (MongoClient client = MongoClients.create()) {
            MongoDatabase db = client.getDatabase("bd_comandes");
            MongoCollection<Document> collection = db.getCollection("clients");
            exercisi3(collection);
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

    public static String askNonBlank(Scanner sc, String question) {
        String v;

        do {
            System.out.print(question);
            v = sc.nextLine();
        } while (v.isBlank());

        return v;
    }
}
