package com.edgarluque.m6.activitat2_4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Moble {
    private double ample;
    private double llarg;
    private int color;

    public Moble(double ample, double llarg, int color) {
        this.ample = ample;
        this.llarg = llarg;
        this.color = color;
    }

    public void save(DataOutputStream stream) throws IOException {
        stream.writeDouble(ample);
        stream.writeDouble(llarg);
        stream.writeInt(color);
    }

    static Moble load(DataInputStream stream) throws IOException {
        return new Moble(stream.readDouble(), stream.readDouble(), stream.readInt());
    }

    static Moble fromInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ample: ");
        double ample = scanner.nextDouble();
        System.out.print("Llarg: ");
        double llarg = scanner.nextDouble();
        System.out.print("Color: ");
        int color = scanner.nextInt();
        return new Moble(ample, llarg, color);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Moble{");
        sb.append("ample=").append(ample);
        sb.append(", llarg=").append(llarg);
        sb.append(", color=").append(color);
        sb.append('}');
        return sb.toString();
    }

    public double getAmple() {
        return ample;
    }

    public double getLlarg() {
        return llarg;
    }

    public int getColor() {
        return color;
    }
}
