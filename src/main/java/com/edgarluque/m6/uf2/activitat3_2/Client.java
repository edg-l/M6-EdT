package com.edgarluque.m6.uf2.activitat3_2;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Entity
@Table(name = "act32_client")
public class Client implements Serializable {
    @Id
    private int dni;
    private String nom;
    private boolean premium;
    @OneToMany(mappedBy = "client")
    private Set<Comanda> comandes;

    public Client() {}

    public Client(int dni, String nom, boolean premium) {
        this.dni = dni;
        this.nom = nom;
        this.premium = premium;
        this.comandes = new HashSet<>();
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

    public Set<Comanda> getComandes() {
        return comandes;
    }

    public void setComandes(Set<Comanda> comandes) {
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
