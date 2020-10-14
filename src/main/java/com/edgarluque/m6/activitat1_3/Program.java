package com.edgarluque.m6.activitat1_3;

import java.io.File;

public class Program {

	public static void main(String[] args) {
		if(args.length < 2) {
			System.out.println("Es necessiten dos arguments.");
			return;
		}
		
		File a = new File(args[0]);
		File b = new File(args[1]);
		
		if(!a.isDirectory() || !b.isDirectory()) {
			System.out.println("El primer o segon argument no es un directori.");
		}
		
		compareDirs(a, b);
	}

	static void compareDirs(File a, File b) {
		if(!a.isDirectory() || !b.isDirectory()) {
			System.out.println("Ambdós fitxers han de ser directoris.");
			return;
		}
		
		File[] bFiles = b.listFiles();
		
		for(File x: a.listFiles()) {
			if(x.isDirectory())
				continue;
			boolean found = false;
			for(File y: bFiles) {
				if(y.isDirectory())
					continue;
				if(x.getName().equals(y.getName())) {
					System.out.println("Existeix en els dos directoris: " + x.getName());
					if(x.lastModified() != y.lastModified()) {
						System.out.println("- El fitxer A es mes " + (x.lastModified() < y.lastModified() ? "antic" : "nou") + " que B.");
					}
					else 
						System.out.println("- Tenen la mateixa data");
					if(x.length() != y.length()) {
						System.out.println("- El fitxer A es mes " + (x.length() < y.length() ? "petit" : "gran") + " que B.");
					}
					else
						System.out.println("- Tenen la mateixa mida.");
					found = true;
					break;					
				}
			}
			if(!found)
				System.out.println("Existeix en el directori A, pero no en el B: " + x.getAbsolutePath());
		}
	}
}
