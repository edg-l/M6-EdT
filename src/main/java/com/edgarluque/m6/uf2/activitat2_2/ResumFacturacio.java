package com.edgarluque.m6.uf2.activitat2_2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ResumFacturacio {
    public static void generateFacturacio(Connection conn) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Introdueix l'any:");
        int any = sc.nextInt();
        if(any < 0) {
            System.out.println("El any es invalid");
            return;
        }
        System.out.println("Introdueix el mes:");
        int mes = sc.nextInt();
        if(mes < 1 || mes > 31) {
            System.out.println("El mes es invalid");
            return;
        }

        try (PreparedStatement stmt = conn.prepareStatement(
                "call crea_resum_facturacio(?,?)"
        )) {
            stmt.setInt(1, mes);
            stmt.setInt(2, any);
            stmt.execute();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
