package com.edgarluque.m6.activitat3_2;

import com.edgarluque.m6.util.MenuBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        MenuBuilder menu = new MenuBuilder();
        menu.addTitle("Activitat 3.2", true);
        menu.addEmptyLine();
        menu.addSeparator();
        menu.addLine("0 - Sortir", false);
        menu.addLine("1 - Llegir d'un fitxer XML pel mètode seqüencial", false);
        menu.addLine("2 - Llegir d'un fitxer XML pel mètode sintàctic", false);
        menu.addLine("3 - Mostrar per pantalla totes les assignatures amb les seves dades", false);
        menu.addLine("4 - Afegir una assignatura", false);
        menu.addLine("5 - Afegir un alumne a una assignatura", false);
        menu.addLine("6 - Guardar a disc en XML amb les assignatures", false);
        menu.addSeparator();

        List<Assignatura> assignaturas = new ArrayList<>();

        while (menu.isRunning()) {
            menu.print();

            switch (menu.askOption()) {
                case 0:
                    menu.requestStop();
                    break;
                case 1: {
                    Scanner s = new Scanner(System.in);
                    System.out.println("Path al fitxer:");
                    String path = s.next();
                    try {
                        assignaturas = Assignatura.llegirSequencial(path);
                        System.out.printf("Afegit %d assignatures\n", assignaturas.size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 2: {
                    Scanner s = new Scanner(System.in);
                    System.out.println("Path al fitxer:");
                    String path = s.next();
                    try {
                        assignaturas = Assignatura.llegirSintactic(path);
                        System.out.printf("Afegit %d assignatures\n", assignaturas.size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 3:
                    for (Assignatura assignatura : assignaturas)
                        assignatura.imprimir();
                    break;
                case 4:
                    assignaturas.add(Assignatura.fromInput());
                    break;
                case 5:
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Index de l'assignatura a la que afegir un alumne:");
                    int index = sc.nextInt();
                    if (index > 0 && index < assignaturas.size())
                        assignaturas.get(index).getAlumnes().add(Alumne.fromInput());
                    else
                        System.out.println("Index invalid");
                    break;
                case 6: {
                    Scanner s = new Scanner(System.in);
                    System.out.println("Path al fitxer:");
                    String path = s.next();

                    DocumentBuilder docBuilder = null;
                    try {
                        docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                        Document doc = docBuilder.newDocument();
                        Element nodeAssignatures = doc.createElement("assignatures");
                        for(Assignatura assignatura: assignaturas) {
                            assignatura.guardar(nodeAssignatures, doc);
                        }
                        doc.appendChild(nodeAssignatures);

                        Transformer trans = TransformerFactory.newInstance().newTransformer();
                        StreamResult result = new StreamResult(new File(path));
                        DOMSource source = new DOMSource(doc);
                        trans.transform(source, result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                }
                default:
                    System.out.println("Opció invalida.");
            }
        }
    }
}
