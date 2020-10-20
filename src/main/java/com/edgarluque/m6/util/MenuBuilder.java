package com.edgarluque.m6.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MenuBuilder {
    /**
     * Stores information for each line added.
     */
    private class TextLine {
        private boolean centered;
        private String text;
        private char wall;
        private char fill;
        private int frameWidth;


        public TextLine(boolean centered, String text, char wall, char fill) {
            this.centered = centered;
            this.text = text;
            this.wall = wall;
            this.fill = fill;
            this.frameWidth = 0;
        }

        public void setFrameWidth(int frameWidth) {
            this.frameWidth = frameWidth;
        }

        public int getTextLength() {
            return text.length();
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            int length = text.length();
            int start = 2;

            if (centered)
                start = frameWidth / 2 - (length / 2);

            for (int i = 0; i < frameWidth; i++) {
                if (i == 0 || i == frameWidth - 1) {
                    builder.append(wall);
                    continue;
                }

                if (i < start || i >= start + length) {
                    builder.append(fill);
                    continue;
                }

                if (i >= start && i < start + length) {
                    builder.append(text.charAt(i - start));
                    continue;
                }
            }

            return builder.toString();
        }
    }

    private Scanner scanner;
    private boolean running;
    private List<TextLine> lines = null;
    private int minFrameSize;

    public MenuBuilder() {
        running = true;
        lines = new LinkedList<>();
        minFrameSize = 0;
        scanner = new Scanner(System.in);
    }

    public MenuBuilder(int minFrameSize) {
        this();
        this.minFrameSize = minFrameSize;
    }

    public boolean addLine(String text, boolean centered) {
        return lines.add(new TextLine(centered, text, '|', ' '));
    }

    public boolean addEmptyLine() {
        return lines.add(new TextLine(false, "", '|', ' '));
    }

    public boolean addTitle(String text, boolean centered) {
        return lines.add(new TextLine(centered, String.format(" %s ", text), '=', '='));
    }

    public boolean addSeparator() {
        return lines.add(new TextLine(false, "=", '=', '='));
    }

    @Override
    public String toString() {
        int maxWidth = minFrameSize;

        // First pass to get the longest line.
        for (TextLine line : lines) {
            int length = line.getTextLength();
            if (length > maxWidth)
                maxWidth = length;
        }

        final StringBuilder sb = new StringBuilder();

        for (TextLine line : lines) {
            line.setFrameWidth(maxWidth + 4);
            sb.append(line.toString());
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

    public void print() {
        System.out.println(toString());
    }

    public void requestStop() {
        this.running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public int askOption() {
        System.out.print("Opció: ");
        return scanner.nextInt();
    }

    public void waitForInput() {
        System.out.println("Presiona qualsevol tecla per continuar.");
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
