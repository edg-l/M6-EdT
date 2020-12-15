package com.edgarluque.m6.examen_uf1;

import com.edgarluque.m6.activitat3_2.Assignatura;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Pelicula {
    String nom;
    List<Ressenya> ressenyes = new ArrayList<>();

    public void guardar(Element root, Document doc) {
        Element nodeNom = doc.createElement("nom");
        Element nodeRessenyes = doc.createElement("ressenyes");

        nodeNom.setTextContent(nom);

        for (Ressenya r : ressenyes) {
            Element ressenya = doc.createElement("ressenya");
            r.guardar(ressenya, doc);
        }

        root.appendChild(nodeNom);
        root.appendChild(nodeRessenyes);
    }

    public static void guardar(String ruta, List<Pelicula> peliculas) throws ParserConfigurationException {
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element nodePelicules = doc.createElement("pelicules");
            for (Pelicula pelicula : peliculas) {
                pelicula.guardar(nodePelicules, doc);
            }
            doc.appendChild(nodePelicules);

            Transformer trans = TransformerFactory.newInstance().newTransformer();
            StreamResult result = new StreamResult(new File(ruta));
            DOMSource source = new DOMSource(doc);
            trans.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Implementa un mètode que, rebent per paràmetre la ruta de l'XML,
    retorni pel mètode sintàctic la puntuació de la primera ressenya de la primera pel·lícula de la llista
     */

    public static int primeraPuntuacio(String ruta) throws ParserConfigurationException, IOException,
            SAXException, XPathExpressionException {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(ruta);
        XPath xPath = XPathFactory.newInstance().newXPath();

        Node nodePuntuacio = (Node) xPath.compile("/pelicules/pelicula/ressenyes/ressenya/puntuacio[1]")
                .evaluate(doc, XPathConstants.NODE);
        return Integer.parseInt(nodePuntuacio.getTextContent());
    }

    // Busca el node "nom" recursivament.
    public static Node findChildNode(Node node, String nom) {
        if(node.getNodeName().equals(nom))
            return node;

        NodeList nodes = node.getChildNodes();

        for(int i = 0; i < nodes.getLength(); i++) {
            Node sub = nodes.item(i);

            if(sub.getNodeName().equals(nom))
                return sub;

            Node subsub = findChildNode(sub, nom);
            if(subsub != null)
                return subsub;
        }

        return null;
    }

    public static int primeraPuntuacioSeq(String ruta) throws ParserConfigurationException, IOException,
            SAXException, XPathExpressionException {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(ruta);

        NodeList nodeList = doc.getDocumentElement().getChildNodes();

        Node node = findChildNode(doc.getDocumentElement(), "puntuacio");

        if(node != null)
            return Integer.parseInt(node.getTextContent());
        return -1;
    }
}