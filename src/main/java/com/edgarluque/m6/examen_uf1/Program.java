package com.edgarluque.m6.examen_uf1;

import java.io.*;
import java.util.Date;


public class Program {
    public static void main(String[] args) {

    }

    /*
    Implementeu un mètode que rebi per paràmetre una ruta i un array d'enters.
    El mètode emmagatzemarà els enters a disc a la ruta proporcionada pel mètode orientat per caràcters, tenint en compte
    en tot moment que cal poder-los recuperar pel mateix mètode posteriorment. No cal que implementis la recuperació.
     */

    public static void guardarEnters(String ruta, int[] enters) {
        try(OutputStreamWriter stream = new OutputStreamWriter(new FileOutputStream(ruta))) {
            for(int x: enters) {
                stream.write(Integer.toString(x));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
