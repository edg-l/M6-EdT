package com.edgarluque.m6.activitat3_2;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Assignatura {
    public int numero;
    public String nom;
    public int durada;
    private List<Alumne> alumnes;

    public Assignatura(int numero, String nom, int durada, List<Alumne> alumnes) {
        this.numero = numero;
        this.nom = nom;
        this.durada = durada;
        this.alumnes = alumnes;
    }

    public List<Alumne> getAlumnes() {
        return alumnes;
    }

    public static Assignatura fromInput() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nom de l'assignatura:");
        String nom = sc.next();
        System.out.println("Durada:");
        int durada = sc.nextInt();
        System.out.println("Numero:");
        int numero = sc.nextInt();
        return new Assignatura(numero, nom, durada, new ArrayList<>());
    }

    public void imprimir() {
        StringBuilder sb = new StringBuilder();
        sb.append("---Assignatura---").append(System.lineSeparator());
        sb.append("Nom: ").append(nom).append(System.lineSeparator());
        sb.append("Durada: ").append(durada).append(System.lineSeparator());
        sb.append("Numero: ").append(numero).append(System.lineSeparator());
        System.out.print(sb.toString());

        for (Alumne alumne : alumnes) {
            alumne.imprimir();
        }
    }

    public void guardar(Element root, Document doc) {
        Element nodeNom = doc.createElement("nom");
        Element nodeNumero = doc.createElement("numero");
        Element nodeDurada = doc.createElement("durada");
        Element nodeAlumnes = doc.createElement("alumnes");

        nodeNom.setTextContent(nom);
        nodeNumero.setTextContent(Integer.toString(numero));
        nodeDurada.setTextContent(Integer.toString(durada));

        root.appendChild(nodeNom);
        root.appendChild(nodeNumero);
        root.appendChild(nodeDurada);

        for(Alumne alumne: alumnes) {
            Element nodeAlumne = doc.createElement("alumne");
            alumne.guardar(nodeAlumne, doc);
            nodeAlumnes.appendChild(nodeAlumne);
        }

        root.appendChild(nodeAlumnes);
    }

    public static Assignatura fromNode(Node node) {
        int numero = 0;
        String nom = null;
        int durada = 0;
        List<Alumne> alumnes = new ArrayList<>();

        NodeList nodes = node.getChildNodes();

        for (int i = 0, n = nodes.getLength(); i < n; i++) {
            Node item = nodes.item(i);
            String nodeName = item.getNodeName();
            switch (nodeName) {
                case "numero":
                    numero = Integer.parseInt(item.getTextContent());
                    break;
                case "durada":
                    durada = Integer.parseInt(item.getTextContent());
                    break;
                case "nom":
                    nom = item.getTextContent();
                    break;
                case "alumnes": {
                    NodeList nodeAlumnes = item.getChildNodes();
                    for (int j = 0, k = nodeAlumnes.getLength(); j < k; j++) {
                        Node nodeAlumne = nodeAlumnes.item(j);
                        if (nodeAlumne.getNodeName().equals("alumne"))
                            alumnes.add(Alumne.fromNode(nodeAlumne));
                    }
                    break;
                }
            }
        }

        return new Assignatura(numero, nom, durada, alumnes);
    }

    public static Assignatura fromNodeSintactic(Node node) throws XPathExpressionException {
        List<Alumne> alumnes = new ArrayList<>();

        XPath xPath = XPathFactory.newInstance().newXPath();

        Node nodeNum = (Node) xPath.compile("./numero").evaluate(node, XPathConstants.NODE);
        Node nodeNom = (Node) xPath.compile("./nom").evaluate(node, XPathConstants.NODE);
        Node nodeDurada = (Node) xPath.compile("./durada").evaluate(node, XPathConstants.NODE);

        NodeList nodeAlumnes = (NodeList) xPath.compile("./alumnes/alumne").evaluate(node, XPathConstants.NODESET);

        for(int i = 0, n = nodeAlumnes.getLength(); i < n; i++) {
            Node nodeAlumne = nodeAlumnes.item(i);
            alumnes.add(Alumne.fromNodeSintactic(nodeAlumne));
        }

        int numero = Integer.parseInt(nodeNum.getTextContent());
        int durada = Integer.parseInt(nodeDurada.getTextContent());
        String nom = nodeNom.getTextContent();

        return new Assignatura(numero, nom, durada, alumnes);
    }

    public static List<Assignatura> llegirSequencial(String path) throws ParserConfigurationException, IOException, SAXException {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(path);

        List<Assignatura> assignaturas = new ArrayList<>();
        NodeList nodeList = doc.getDocumentElement().getChildNodes();

        for(int i = 0, n = nodeList.getLength(); i < n; i++) {
            Node node = nodeList.item(i);

            if(node.getNodeName().equals("assignatura"))
                assignaturas.add(Assignatura.fromNode(node));
        }

        return assignaturas;
    }

    public static List<Assignatura> llegirSintactic(String path) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(path);
        XPath xPath = XPathFactory.newInstance().newXPath();
        XPathExpression expr = xPath.compile("/assignatures/assignatura");
        List<Assignatura> assignaturas = new ArrayList<>();
        NodeList nodeAssignatures = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

        for(int i = 0, n = nodeAssignatures.getLength(); i < n; i++) {
            assignaturas.add(Assignatura.fromNodeSintactic(nodeAssignatures.item(i)));
        }

        return assignaturas;
    }
}
