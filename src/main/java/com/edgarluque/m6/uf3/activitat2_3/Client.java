package com.edgarluque.m6.uf3.activitat2_3;

import com.edgarluque.m6.uf3.activitat2_2.Assignatura;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private String nif;
    private String nom;
    private int totalFacturacio;
    private String telefon;
    private String correu;
    private List<Comanda> comandes;

    public Client(String nif, String nom, int totalFacturacio) {
        this.nif = nif;
        this.nom = nom;
        this.totalFacturacio = totalFacturacio;
        this.telefon = null;
        this.correu = null;
        this.comandes = new ArrayList<>();
    }

    public Client(Document doc) {
        this.nif = doc.getString("nif");
        this.nom = doc.getString("nom");
        this.totalFacturacio = doc.getInteger("totalFacturacio");

        if (doc.containsKey("telefon"))
            this.telefon = doc.getString("telefon");
        else
            this.telefon = null;

        if (doc.containsKey("correu"))
            this.correu = doc.getString("correu");
        else
            this.correu = null;

        this.comandes = new ArrayList<>();

        if(doc.containsKey("comandes")) {
            List<Document> docComandes = doc.getList("comandes", Document.class);

            for (Document d : docComandes) {
                comandes.add(new Comanda(d));
            }
        }

    }

    public Document toDocument() {
        Document doc = new Document();
        doc.append("nif", nif);
        doc.append("nom", nom);
        doc.append("totalFacturacio", totalFacturacio);

        if (telefon != null)
            doc.append("telefon", telefon);
        if (correu != null)
            doc.append("correu", correu);

        if(!comandes.isEmpty()) {
            List<Document> docComandes = new ArrayList<>(comandes.size());

            for (Comanda c : comandes) {
                docComandes.add(c.toDocument());
            }

            doc.append("comandes", docComandes);
        }

        return doc;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getTotalFacturacio() {
        return totalFacturacio;
    }

    public void setTotalFacturacio(int totalFacturacio) {
        this.totalFacturacio = totalFacturacio;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getCorreu() {
        return correu;
    }

    public void setCorreu(String correu) {
        this.correu = correu;
    }

    public List<Comanda> getComandes() {
        return comandes;
    }

    public void setComandes(List<Comanda> comandes) {
        this.comandes = comandes;
    }
}
