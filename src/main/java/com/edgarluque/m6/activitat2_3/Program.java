package com.edgarluque.m6.activitat2_3;

import java.io.*;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Falta argument path.");
            return;
        }

        File file = new File(args[0]);

        System.out.println("1 - Emmagatzamar");
        System.out.println("2 - Recuperar");
        System.out.println("Escull una opcio:");
        Scanner scanner = new Scanner(System.in);
        int opcio = scanner.nextInt();

        if (opcio == 1) {
            try (DataOutputStream stream = new DataOutputStream(new FileOutputStream(file))) {
                Treballador.fromInput().save(stream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (opcio == 2) {
            try (DataInputStream stream = new DataInputStream(new FileInputStream(file))) {
                Treballador treballador = Treballador.load(stream);
                System.out.println(treballador);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Opció invalida.");
        }
    }
}
