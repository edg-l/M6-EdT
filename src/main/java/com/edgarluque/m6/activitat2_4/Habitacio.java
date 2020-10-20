package com.edgarluque.m6.activitat2_4;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class Habitacio {
    private String nom;
    private double ample;
    private double llarg;
    private ArrayList<Moble> mobles;

    public Habitacio(String nom, double ample, double llarg) {
        this.nom = nom;
        this.ample = ample;
        this.llarg = llarg;
        this.mobles = new ArrayList<>();
    }

    void addMoble(Moble moble) {
        this.mobles.add(moble);
    }

    public void save(File habitacioFile, File moblesFile) {
        try (
                DataOutputStream propsStream = new DataOutputStream(new FileOutputStream(habitacioFile));
                DataOutputStream moblesStream = new DataOutputStream(new FileOutputStream(moblesFile))
        ) {
            Properties props = new Properties();
            props.setProperty("nom", nom);
            props.setProperty("ample", Double.toString(ample));
            props.setProperty("llarg", Double.toString(llarg));
            props.store(propsStream, "");

            moblesStream.writeInt(mobles.size());
            for(Moble moble: mobles)
                moble.save(moblesStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Habitacio load(File habitacioFile, File moblesFile) {
        try (
                DataInputStream propsStream = new DataInputStream(new FileInputStream(habitacioFile));
                DataInputStream moblesStream = new DataInputStream(new FileInputStream(moblesFile))
        ) {
            Properties props = new Properties();
            props.load(propsStream);
            Habitacio habitacio = new Habitacio(
                    props.getProperty("nom"),
                    Double.parseDouble(props.getProperty("ample")),
                    Double.parseDouble(props.getProperty("llarg"))
            );

            for (int i = 0, q = moblesStream.readInt(); i < q; i++)
                habitacio.addMoble(Moble.load(moblesStream));

            return habitacio;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getNom() {
        return nom;
    }

    public double getAmple() {
        return ample;
    }

    public double getLlarg() {
        return llarg;
    }

    public ArrayList<Moble> getMobles() {
        return mobles;
    }
}
