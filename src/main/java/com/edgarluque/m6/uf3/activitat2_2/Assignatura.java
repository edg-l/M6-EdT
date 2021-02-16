package com.edgarluque.m6.uf3.activitat2_2;

import org.bson.Document;

public class Assignatura {
    private String nom;
    private int hores;

    public Assignatura(String nom, int hores) {
        this.nom = nom;
        this.hores = hores;
    }

    public Assignatura(Document doc) {
        this.nom = doc.getString("nom");
        this.hores = doc.getInteger("hores_setmana");
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getHores() {
        return hores;
    }

    public void setHores(int hores) {
        this.hores = hores;
    }

    public Document toDocument() {
        return new Document("nom", nom)
                .append("hores_setmana", hores);
    }

    @Override
    public String toString() {
        return "Assignatura{" +
                "nom='" + nom + '\'' +
                ", hores=" + hores +
                '}';
    }
}
