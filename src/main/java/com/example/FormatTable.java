package com.example;

import java.util.*;

public class FormatTable {
    private int[] align = new int[0];
    private int[] width = new int[0];
    private final List<String[]> lines = new LinkedList<>();

    public FormatTable(String[] header, int[] align) {
        setHeader(header);
        if(this.align.length != header.length) {
            if (align == null) this.align = new int[header.length];
            else this.align = align;
        }
    }

    public FormatTable(String... header) {
        this(header, null);
    }

    public FormatTable setHeader(String... header) {
        if(header.length != lines.get(0).length) {
            width = new int[header.length];
            lines.clear();
            addLine(header);
            return this;
        }
        lines.set(0, calculateWidth(header));
        return this;
    }

    public FormatTable setAlign(int... align) {
        this.align = align;
        return this;
    }

    public FormatTable addLine(String... input){
        lines.add(calculateWidth(input));
        return this;
    }

    @Override
    public String toString() {
        int lineLength = width.length - 1 + Arrays.stream(width).sum();
        StringBuilder sb = new StringBuilder();
        Iterator<String[]> iterator = lines.iterator();
        appendFormatted(sb, iterator.next());
        sb.append("\n").append("-".repeat(lineLength)).append("\n");
        while(iterator.hasNext()) {
            String[] tmp = iterator.next();
            if (!iterator.hasNext()) sb.append("-".repeat(lineLength)).append("\n");
            appendFormatted(sb, tmp);
        }
        return sb.toString();
    }

    public static String appendFormatted(String value, int align, int width){
        if (value.length() > width) return value.substring( 0, width) + ' ';
        int before = 0, after;
        if (align == 0) before = (width - value.length()) / 2;
        else if (align == 1) before = width - value.length();
        after = width - value.length() - before + 1;
        return " ".repeat(before)+value+" ".repeat(after);
    }

    private void appendFormatted(StringBuilder sb, String[] line) {
        for (int i = 0; i < line.length; i++)
            sb.append(appendFormatted(line[i], align[i], width[i]));
    }

    private String[] calculateWidth(String[] line){
        for (int i = 0; i < line.length; i++)
            width[i] = Math.max(width[i], line[i].length());
        return line;
    }
}
