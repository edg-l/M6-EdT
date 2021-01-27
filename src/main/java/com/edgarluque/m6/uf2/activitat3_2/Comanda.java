package com.edgarluque.m6.uf2.activitat3_2;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

@Entity
@Table(name = "act32_comanda")
public class Comanda implements Serializable {
    @Id
    @Column(name = "num_comanda")
    private int numComanda;
    @Column(name = "preu_total", precision = 8, scale = 2)
    private BigDecimal preuTotal;
    private Date data;
    @ManyToOne
    @JoinColumn(name = "dni_client", nullable = false)
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
                '}';
    }

    public static Comanda fromInput(Client client) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Numero comanda:");
        int num = sc.nextInt();

        System.out.println("Preu total");
        BigDecimal preu = sc.nextBigDecimal();
        sc.nextLine();

        System.out.println("Data (yyyy-MM-dd):");
        Date data;
        while (true) {
            try {
                System.out.println("Introdueix la data (yyyy-MM-dd)");
                data = Date.valueOf(LocalDate.parse(sc.nextLine()));
                break;
            } catch (DateTimeParseException ignored) {
            }
        }

        return new Comanda(num, preu, data, client);
    }
}
