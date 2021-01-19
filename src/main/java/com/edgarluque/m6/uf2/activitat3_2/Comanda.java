package com.edgarluque.m6.uf2.activitat3_2;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "act32_comanda")
public class Comanda implements Serializable {
    @Id
    @Column(name = "num_comanda")
    private int numComanda;
    @Column(name = "preu_total", precision = 8, scale = 2)
    private BigDecimal preuTotal;
    private Date data;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "dni_client")
    private Client client;

    public Comanda() {}

    public Comanda(int numComanda, BigDecimal preuTotal, Date data, Client client) {
        this.numComanda = numComanda;
        this.preuTotal = preuTotal;
        this.data = data;
        this.client = client;
    }

    public int getNumComanda() {
        return numComanda;
    }

    public void setNumComanda(int numComanda) {
        this.numComanda = numComanda;
    }

    public BigDecimal getPreuTotal() {
        return preuTotal;
    }

    public void setPreuTotal(BigDecimal preuTotal) {
        this.preuTotal = preuTotal;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Comanda{" +
                "numComanda=" + numComanda +
                ", preuTotal=" + preuTotal +
                ", data=" + data +
                ", client=" + client +
                '}';
    }
}
