package com.edgarluque.m6.activitat3_2;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.Scanner;

public class Alumne {
    public String nom;
    public String dni;
    public boolean repetidor;

    public Alumne(String nom, String dni, boolean repetidor) {
        this.nom = nom;
        this.dni = dni;
        this.repetidor = repetidor;
    }

    public static Alumne fromInput() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nom de l'alumne:");
        String nom = sc.next();
        System.out.println("DNI de l'alumne:");
        String dni = sc.next();
        System.out.println("Es repetidor (s/n):");
        boolean repetidor = sc.next().equalsIgnoreCase("s");
        return new Alumne(nom, dni, repetidor);
    }

    public void imprimir() {
        StringBuilder sb = new StringBuilder();
        sb.append("---Alumne---").append(System.lineSeparator());
        sb.append("Nom: ").append(nom).append(System.lineSeparator());
        sb.append("DNI: ").append(dni).append(System.lineSeparator());
        sb.append("Repetidor: ").append(repetidor).append(System.lineSeparator());
        System.out.print(sb.toString());
    }

    public static Alumne fromNode(Node node) {
        NodeList nodeList = node.getChildNodes();

        String nom = null;
        String dni = null;
        boolean repetidor = false;

        for (int i = 0, n = nodeList.getLength(); i < n; i++) {
            Node item = nodeList.item(i);
            String nodeName = item.getNodeName();

            switch (nodeName) {
                case "nom":
                    nom = item.getTextContent();
                    break;
                case "dni":
                    dni = item.getTextContent();
                    break;
                case "repetidor":
                    repetidor = Boolean.parseBoolean(item.getTextContent());
            }
        }

        return new Alumne(nom, dni, repetidor);
    }

    public static Alumne fromNodeSintactic(Node node) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        Node nodeNom = (Node) xPath.compile("./nom").evaluate(node, XPathConstants.NODE);
        Node nodeDni = (Node) xPath.compile("./dni").evaluate(node, XPathConstants.NODE);
        Node nodeRepetidor = (Node) xPath.compile("./repetidor").evaluate(node, XPathConstants.NODE);

        String nom = nodeNom.getTextContent();
        String dni = nodeDni.getTextContent();
        boolean repetidor = Boolean.parseBoolean(nodeRepetidor.getTextContent());

        return new Alumne(nom, dni, repetidor);
    }

    public void guardar(Element root, Document doc) {
        Element nodeNom = doc.createElement("nom");
        Element nodeDni = doc.createElement("dni");
        Element nodeRepetidor = doc.createElement("repetidor");

        nodeNom.setTextContent(nom);
        nodeDni.setTextContent(dni);
        nodeRepetidor.setTextContent(Boolean.toString(repetidor));

        root.appendChild(nodeNom);
        root.appendChild(nodeDni);
        root.appendChild(nodeRepetidor);
    }
}
