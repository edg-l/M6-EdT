package com.edgarluque.m6.uf3.activitat2_2;

import com.edgarluque.m6.util.MenuBuilder;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class Program {
    public static void main(String[] args) {
        MenuBuilder menu = new MenuBuilder();
        menu.addTitle("UF3", true);
        menu.addEmptyLine();
        menu.addLine("1. Afegir assignatures.", false);
        menu.addLine("2. Obtenir assignatures amb hores > 2.", false);
        menu.addEmptyLine();
        menu.addLine("0. Sortir", false);
        menu.addSeparator();

        try (MongoClient client = MongoClients.create()) {
            MongoDatabase db = client.getDatabase("bd_prova");
            MongoCollection<Document> collection = db.getCollection("assignatures");

            while (menu.isRunning()) {
                menu.print();

                switch (menu.askOption()) {
                    case 1:
                        exercisi1(collection);
                        menu.waitForInput();
                        break;
                    case 2:
                        exercisi2(collection);
                        menu.waitForInput();
                        break;
                    case 0:
                        menu.requestStop();;
                        break;
                }
            }
        }
    }

    public static void exercisi1(MongoCollection<Document> collection) {
        collection.insertOne(new Assignatura("Accés a dades", 5).toDocument());
        collection.insertOne(new Assignatura("Sistemes de gestió empresarial,", 3).toDocument());
    }

    public static void exercisi2(MongoCollection<Document> collection) {
        try (MongoCursor<Document> cursor
                     = collection.find(Filters.gt("hores_setmana", 2)).iterator()) {
            while (cursor.hasNext()) {
                Assignatura assignatura = new Assignatura(cursor.next());
                System.out.println(assignatura);
            }
        }
    }
}
