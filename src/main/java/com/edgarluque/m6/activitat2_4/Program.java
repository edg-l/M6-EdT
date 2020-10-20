package com.edgarluque.m6.activitat2_4;

import com.edgarluque.m6.util.MenuBuilder;

import java.io.File;

public class Program {
    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Falten arguments: path habitacio, path mobles.");
            return;
        }

        File habitacioFile = new File(args[0]);
        File moblesFile = new File(args[1]);

        if (habitacioFile.isDirectory()) {
            System.out.println("El path de l'habitacio no ha de ser un directori.");
            return;
        }

        if (moblesFile.isDirectory()) {
            System.out.println("El path dels mobles no ha de ser un directori.");
            return;
        }

        MenuBuilder menu = new MenuBuilder();
        menu.addTitle("Activitat 2.4", true);
        menu.addEmptyLine();
        menu.addSeparator();
        menu.addLine("0 - Sortir", false);
        menu.addLine("1 - Mostrar per pantalla les dades de l'habitació", false);
        menu.addLine("2 - Afegir un moble a l'habitació", false);
        menu.addLine("3 - Guardar a disc l'habitació", false);
        menu.addLine("4 - Llistar per pantalla tots els mobles de l'habitació, amb totes les seves dades.", false);
        menu.addSeparator();

        Habitacio habitacio;
        if (!habitacioFile.exists()) {
            habitacio = new Habitacio("Sala d'estar", 100, 200);
            habitacio.save(habitacioFile, moblesFile);
        } else {
            habitacio = Habitacio.load(habitacioFile, moblesFile);
        }

        while (menu.isRunning()) {
            menu.print();

            switch (menu.askOption()) {
                case 0:
                    menu.requestStop();
                    break;
                case 1:
                    System.out.println("Nom: " + habitacio.getNom());
                    System.out.println("Amble: " + habitacio.getAmple());
                    System.out.println("LLargada: " + habitacio.getLlarg());
                    menu.waitForInput();
                    break;
                case 2: {
                    Moble moble = Moble.fromInput();
                    habitacio.addMoble(moble);
                    System.out.println("Moble afegit.");
                    menu.waitForInput();
                    break;
                }
                case 3: {
                    habitacio.save(habitacioFile, moblesFile);
                    System.out.println("Habitació guardada.");
                    menu.waitForInput();
                    break;
                }
                case 4: {
                    for (Moble moble : habitacio.getMobles()) {
                        System.out.println(moble.toString());
                    }
                    break;
                }
                default:
                    System.out.println("Opció invalida.");
            }
        }

    }
}
