package com.edgarluque.m6.uf2.activitat3_1;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="act31_persona")
public class Persona implements Serializable {
    @Id
    public int id;

    @Column(name="naixament")
    public Date dataNaixament;

    public Persona(int id, Date dataNaixament) {
        this.id = id;
        this.dataNaixament = dataNaixament;
    }
}
