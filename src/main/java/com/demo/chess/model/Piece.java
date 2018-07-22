package com.demo.chess.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Piece {
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getNegative() {
        return negative;
    }

    public void setNegative(Boolean negative) {
        this.negative = negative;
    }

    private int value;
    private String name;
    private String color;
    private Boolean negative;

    private static final Logger logger = LoggerFactory.getLogger(Piece.class);
}
