package com.edgarluque.m6.uf2.activitat2_2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResumFacturacio {
    public static void generateFacturacio(Connection conn, int any, int mes) {
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
