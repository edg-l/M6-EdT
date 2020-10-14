package com.edgarluque.m6.activitat2_2;

import java.io.*;
import java.util.Scanner;

public class Program {
	public static void main(String[] args) {

		if (args.length < 1) {
			System.out.println("Falten arguments (path al fitxer)");
			return;
		}

		File file = new File(args[0]);

		int option = 0;
		int times = -1;

		Scanner scanner = new Scanner(System.in);

		while (option < 1 || option > 2) {
			System.out.println("1 - Escriure enters.");
			System.out.println("2 - Llegir enters.");
			System.out.print("Escull una opció: ");
			option = scanner.nextInt();
		}

		while (option == 1 && times < 0) {
			System.out.print("Quants nombres entrar: ");
			times = scanner.nextInt();
		}

		if (option == 1) {
			try (DataOutputStream stream = new DataOutputStream(new FileOutputStream(file))) {
				// Escriu quans nombres hi ha per llegir-los despres.
				stream.writeInt(times);
				while (times > 0) {
					System.out.print("Entra un nombre: ");
					stream.writeInt(scanner.nextInt());
					times--;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (option == 2) {
			if (!file.exists() || !file.isFile()) {
				System.out.println("L'arxiu no existeix o no es un fitxer.");
				return;
			}
			try (DataInputStream stream = new DataInputStream(new FileInputStream(file))) {
				// Quants nombres a llegir.
				times = stream.readInt();

				while (times > 0) {
					System.out.println(stream.readInt());
					times--;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Fet.");
	}

}
