package com.edgarluque.m6.activitat2_3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Treballador {
    String nif;
    String nom;
    int sou;
    int edat;

    public Treballador(String nif, String nom, int sou, int edat) {
        this.nif = nif;
        this.nom = nom;
        this.sou = sou;
        this.edat = edat;
    }

    public void save(DataOutputStream stream) throws IOException {
        stream.writeUTF(nif);
        stream.writeUTF(nom);
        stream.writeInt(sou);
        stream.writeInt(edat);
    }

    public static Treballador load(DataInputStream stream) throws IOException {
        return new Treballador(
                stream.readUTF(),
                stream.readUTF(),
                stream.readInt(),
                stream.readInt()
        );
    }

    public static Treballador fromInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introdueix dni:");
        String dni = scanner.nextLine();
        System.out.println("Introdueix nom:");
        String nom = scanner.nextLine();
        System.out.println("Introdueix sou:");
        int sou = scanner.nextInt();
        System.out.println("Introdueix edat:");
        int edat = scanner.nextInt();
        return new Treballador(dni, nom, sou, edat);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Treballador{");
        sb.append("nif='").append(nif).append('\'');
        sb.append(", nom='").append(nom).append('\'');
        sb.append(", sou=").append(sou);
        sb.append(", edat=").append(edat);
        sb.append('}');
        return sb.toString();
    }
}
