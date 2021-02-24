package com.edgarluque.m6.uf3.activitat2_3;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        try (MongoClient client = MongoClients.create()) {
            MongoDatabase db = client.getDatabase("bd_comandes");
            MongoCollection<Document> collection = db.getCollection("clients");
            exercisi2(collection);
        }
    }

    public static void exercisi1(MongoCollection<Document> collection) {
        Client client = new Client("12345678X", "Pere Pons", 0);
        collection.insertOne(client.toDocument());
    }

    public static void exercisi2(MongoCollection<Document> collection) {
        Scanner sc = new Scanner(System.in);


        String nif = askNonBlank("Introdueix el nif: ");
        String nom = askNonBlank("Introdueix el nom: ");

        System.out.println("Introdueix la factoracio total: ");
        int facturacio = sc.nextInt();

        Client client = new Client(nif, nom, facturacio);

        sc.nextLine();

        System.out.println("Introdueix el telefon (opcional): ");
        String telefon = sc.nextLine();

        if(!telefon.isBlank())
            client.setTelefon(telefon);

        System.out.println("Introdueix el correu (opcional): ");
        String correu = sc.nextLine();

        if(!correu.isBlank())
            client.setCorreu(correu);

        collection.insertOne(client.toDocument());
    }

    public static String askNonBlank(String question) {
        Scanner sc = new Scanner(System.in);
        String v = "";

        while (v.isBlank()) {
            System.out.println(question);
            v = sc.nextLine();
        }
        return v;
    }
}
