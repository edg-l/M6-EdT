package com.edgarluque.m6.activitat1_2;

import java.io.File;

public class Program {
	
	static void deleteFile(File f) {
		
		if(!f.delete()) {
			if(f.isDirectory())
				System.out.println("No s'ha pogut eliminar el fitxer: " + f.getAbsolutePath());
			else
				System.out.println("No s'ha pogut eliminar el directori: " + f.getAbsolutePath());
		}
		else {
			if(f.isDirectory())
				System.out.println("S'ha eliminat el directori: " + f.getAbsolutePath());
			else
				System.out.println("S'ha eliminat el fitxer: " + f.getAbsolutePath());
		}
	}

	public static void main(String[] args) {
		if(args.length < 1) {
			System.out.println("Falta l'argument.");
			return;
		}

		File dir = new File(args[0]);
		
		if(!dir.isDirectory()) {
			System.out.println("L'argument ha de ser un directori.");
		}
		
		for(File f: dir.listFiles())
			deleteFile(f);
		deleteFile(dir);
	}

}
