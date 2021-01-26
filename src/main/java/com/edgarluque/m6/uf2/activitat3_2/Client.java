package com.edgarluque.m6.uf2.activitat3_2;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

@Entity
@Table(name = "act32_client")
public class Client implements Serializable {
    @Id
    private int dni;
    private String nom;
    private boolean premium;
    @OneToMany
    @JoinColumn(name = "dni_client")
    private List<Comanda> comandes;

    public Client() {}

    public Client(int dni, String nom, boolean premium) {
        this.dni = dni;
        this.nom = nom;
        this.premium = premium;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public List<Comanda> getComandes() {
        return comandes;
    }

    public void setComandes(List<Comanda> comandes) {
        this.comandes = comandes;
    }

    @Override
    public String toString() {
        return "Client{" +
                "dni=" + dni +
                ", nom='" + nom + '\'' +
                ", premium=" + premium +
                '}';
    }

    public static Client fromInput() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Introdueix el dni:");
        int dni = sc.nextInt();
        sc.nextLine();

        System.out.println("Introdueix el nom:");
        String nom = sc.nextLine();

        System.out.println("Introdueix si es premium (s/n):");
        boolean premium = sc.nextLine().equals("s");

        return new Client(dni, nom, premium);
    }
}
