package com.edgarluque.m6.activitat1_1;

import java.io.File;

public class Program {

	public static void main(String[] args) {
		if(args.length < 1) {
			System.out.println("Missing path.");
			return;
		}
		
		String path = args[0];
		
		File dir = new File(path);
		
		if(!dir.isDirectory()) {
			System.out.println("Path should be a directory.");
			return;
		}
		
		System.out.println("Selected path: " + dir.getAbsolutePath());
		
		System.out.println("Printing directories and files recursively.");
		printFiles(dir);
	}
	
	public static void printFiles(File file) {
		for(File f: file.listFiles()) {
			System.out.println(f.getAbsolutePath());
			if(f.isDirectory())
				printFiles(f);
		}
	}

}
