package com.company.model;

public class Pair {
    private int element1;
    private int element2;

    public Pair(int element1, int element2) {
        this.element1 = element1;
        this.element2 = element2;
    }

    public void setElement1(int element1) {
        this.element1 = element1;
    }

    public void setElement2(int element2) {
        this.element2 = element2;
    }

    public int getElement1() {
        return element1;
    }

    public int getElement2() {
        return element2;
    }

    @Override
    public String toString() {
        return "(" + this.element1 + ";" + this.element2 + ")";
    }
}
