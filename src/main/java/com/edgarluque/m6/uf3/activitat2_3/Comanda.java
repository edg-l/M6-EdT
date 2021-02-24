package com.edgarluque.m6.uf3.activitat2_3;

import org.bson.Document;

import java.util.Date;

public class Comanda {
    private Date dataComanda;
    private int importe;
    private boolean pagada;

    public Comanda(Date dataComanda, int importe, boolean pagada) {
        this.dataComanda = dataComanda;
        this.importe = importe;
        this.pagada = pagada;
    }

    public Comanda(Document doc) {
        this.dataComanda = doc.getDate("data_comanda");
        this.importe = doc.getInteger("import");
        this.pagada = doc.getBoolean("pagada");
    }

    public Document toDocument() {
        Document doc = new Document();
        doc.append("data_comanda", dataComanda);
        doc.append("import", importe);
        doc.append("pagada", pagada);
        return doc;
    }

    public Date getDataComanda() {
        return dataComanda;
    }

    public void setDataComanda(Date dataComanda) {
        this.dataComanda = dataComanda;
    }

    public int getImporte() {
        return importe;
    }

    public void setImporte(int importe) {
        this.importe = importe;
    }

    public boolean isPagada() {
        return pagada;
    }

    public void setPagada(boolean pagada) {
        this.pagada = pagada;
    }
}
