package com.edgarluque.m6.activitat2_4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Habitacio {
    String nom;
    double ample;
    double llarg;
    ArrayList<Moble> mobles;

    public Habitacio(String nom, double ample, double llarg) {
        this.nom = nom;
        this.ample = ample;
        this.llarg = llarg;
        this.mobles = new ArrayList<>();
    }

    void addMoble(Moble moble) {
        this.mobles.add(moble);
    }

    public void save(DataOutputStream propsStream, DataOutputStream stream) throws IOException {
        Properties props = new Properties();
        props.setProperty("nom", nom);
        props.setProperty("ample", Double.toString(ample));
        props.setProperty("llarg", Double.toString(llarg));
        props.store(propsStream, "");
        stream.writeInt(mobles.size());
        for(Moble moble: mobles)
            moble.save(stream);
    }

    public static Habitacio load(DataInputStream stream) throws IOException {
        Habitacio habitacio = new Habitacio(stream.readUTF(), stream.readDouble(), stream.readDouble());
        int quantitat = stream.readInt();
        for (int i = 0; i < quantitat; i++)
            habitacio.addMoble(Moble.load(stream));
        return habitacio;
    }
}
