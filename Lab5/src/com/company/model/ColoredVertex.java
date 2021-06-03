package com.company.model;

public class ColoredVertex {
    int vertex;
    Color color;
    boolean isColored;

    public ColoredVertex(int vertex) {
        this.vertex = vertex;
        this.isColored = false;
    }

    public ColoredVertex(int vertex, Color color) {
        this.vertex = vertex;
        this.color = color;
        this.isColored = true;
    }

    public void setColor(Color color) {
        this.color = color;
        this.setColored();
    }

    public void setColored() {
        isColored = true;
    }

    public int getVertex() {
        return vertex;
    }

    public Color getColor() {
        return color;
    }

    public boolean isColored() {
        return isColored;
    }
}
