package com.edgarluque.m6.examen_uf2;

import javax.persistence.*;

@Entity
@Table(name = "MONSTRE")
public class Monstre {
    @Id
    @Column(name = "id_monstre")
    private int codi;

    private String nom;
    private int atac;
    @ManyToOne
    @JoinColumn(name = "id_nivell")
    private Nivell nivell;

    public Monstre() {
    }

    public Monstre(int codi, String nom, int atac, Nivell nivell) {
        this.codi = codi;
        this.nom = nom;
        this.atac = atac;
        this.nivell = nivell;
    }

    public Monstre(int codi, String nom, int atac) {
        this.codi = codi;
        this.nom = nom;
        this.atac = atac;
        this.nivell = null;
    }

    public int getCodi() {
        return codi;
    }

    public String getNom() {
        return nom;
    }

    public int getAtac() {
        return atac;
    }

    public Nivell getNivell() {
        return nivell;
    }
}

/*

create table NIVELL (
id_nivell int not null,
nom varchar(255) not null,
dificultat int not null,
primary key (id_nivell)
);

create table MONSTRE (
id_monstre INT not null,
nom varchar(255) not null,
atac int not null,
id_nivell int not null,
foreign key (id_nivell) references NIVELL(id_nivell)
);
 */