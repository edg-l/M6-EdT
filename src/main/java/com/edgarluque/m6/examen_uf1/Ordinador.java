package com.edgarluque.m6.examen_uf1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Ordinador implements Serializable {
    String numeroSerie;
    int velocitat;
    boolean portatil;

    /*
    Implementeu un mètode que, rebent per paràmetre una ruta i una llista d'ordinadors,
    emmagatzemi amb el mètode orientat per bytes la llista d'ordinadors en un fitxer indicat per la ruta proporcionada.

    Després, implementeu un mètode que, rebent per paràmetre una ruta, retorni la llista
    d'ordinadors llegida amb el mètode orientat per bytes de la ruta proporcionada. Si el vostre mètode crida a altres
     */

    public Ordinador(String numeroSerie, int velocitat, boolean portatil) {
        this.numeroSerie = numeroSerie;
        this.velocitat = velocitat;
        this.portatil = portatil;
    }

    public static void guardarOrdinadors(String ruta, List<Ordinador> ordinadors) {
        File file = new File(ruta);

        try(DataOutputStream stream = new DataOutputStream(new FileOutputStream(file))) {
            stream.writeInt(ordinadors.size());
            for(Ordinador o: ordinadors) {
                stream.writeUTF(o.numeroSerie);
                stream.writeInt(o.velocitat);
                stream.writeBoolean(o.portatil);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Ordinador> llegirOrdinadors(String ruta) {
        File file = new File(ruta);

        List<Ordinador> ordinadors = new ArrayList<>();

        try(DataInputStream stream = new DataInputStream(new FileInputStream(file))) {
            int mida = stream.readInt();
            // optimitzacio
            ordinadors = new ArrayList<>(mida);
            for(int i = 0; i < mida; i++) {
                String numSerie = stream.readUTF();
                int velocitat = stream.readInt();
                boolean portatil = stream.readBoolean();
                ordinadors.add(new Ordinador(numSerie, velocitat, portatil))
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ordinadors;
    }

}