package com.edgarluque.m6.uf2.activitat3_1;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

@Entity
@Table(name="act31_politic")
public class Politic implements Serializable {
    @Id
    @Column(length = 9)
    public String nif;

    @Column
    public String name;

    @Column
    public Date dataNaixament;

    @Column
    public int sou;

    @Column
    public boolean esCorrupte;

    public Politic() {
    }

    public Politic(String nif, String name, Date dataNaixament, int sou, boolean esCorrupte) {
        this.nif = nif;
        this.name = name;
        this.dataNaixament = dataNaixament;
        this.sou = sou;
        this.esCorrupte = esCorrupte;
    }

    public static Politic fromInput() {
        Scanner sc = new Scanner(System.in);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println("Introdueix el nif:");
        String nif = sc.nextLine();

        System.out.println("Nom:");
        String nom = sc.nextLine();

        Date data;
        while (true) {
            try {
                System.out.println("Introdueix la data de naixament (yyyy-MM-dd)");
                data = formatter.parse(sc.nextLine());
                break;
            } catch (ParseException ignored) {
            }
        }

        System.out.println("Es corrupte (s/n)?");
        boolean esCorrupte = sc.nextLine().equals("s");

        System.out.println("Sou:");
        int sou = sc.nextInt();

        return new Politic(nif, nom, data, sou, esCorrupte);
    }

    @Override
    public String toString() {
        return "Politic{" +
                "nif='" + nif + '\'' +
                ", name='" + name + '\'' +
                ", dataNaixament=" + dataNaixament +
                ", sou=" + sou +
                ", esCorrupte=" + esCorrupte +
                '}';
    }
}
