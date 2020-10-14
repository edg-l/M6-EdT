package com.edgarluque.m6.activitat2_4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Moble {
    double ample;
    double llarg;
    int color;

    public Moble(double ample, double llarg, int color) {
        this.ample = ample;
        this.llarg = llarg;
        this.color = color;
    }

    public void save(DataOutputStream stream) throws IOException {
        stream.writeDouble(ample);
        stream.writeDouble(llarg);
        stream.writeDouble(color);
    }

    static Moble load(DataInputStream stream) throws IOException {
        return new Moble(stream.readDouble(), stream.readDouble(), stream.readInt());
    }
}
