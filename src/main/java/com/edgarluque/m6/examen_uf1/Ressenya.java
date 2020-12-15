package com.edgarluque.m6.examen_uf1;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Ressenya {
    int puntuacio;
    String text;

    public void guardar(Element root, Document doc) {
        Element nodePuntuacio = doc.createElement("puntuacio");
        Element nodeText = doc.createElement("text");

        nodePuntuacio.setTextContent(Integer.toString(puntuacio));
        nodeText.setTextContent(text);

        root.appendChild(nodePuntuacio);
        root.appendChild(nodeText);
    }
}