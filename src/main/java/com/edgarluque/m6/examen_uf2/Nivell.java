package com.edgarluque.m6.examen_uf2;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "NIVELL")
public class Nivell {
    @Id
    @Column(name = "id_nivell")
    private int codi;
    private String nom;
    private int dificultat;
    @OneToMany(mappedBy = "nivell")
    private Set<Monstre> monstres;

    public Nivell() {
    }

    public Nivell(int codi, String nom, int dificultat) {
        this.codi = codi;
        this.nom = nom;
        this.dificultat = dificultat;
        this.monstres = new HashSet<>();
    }

    public String getNom() {
        return nom;
    }

    public int getCodi() {
        return codi;
    }

    public int getDificultat() {
        return dificultat;
    }

    public Set<Monstre> getMonstres() {
        return monstres;
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