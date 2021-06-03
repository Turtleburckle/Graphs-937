package com.company.model;

public class Color {
    private String name;
    private boolean used;
    private String initial;
    private int index;

    public Color(String name, String initial,int index){
        this.name = name;
        this.used = false;
        this.initial = initial;
        this.index = index;

    }

    public String getName() {
        return name;
    }

    public boolean isUsed() { return used; }

    public void setUsed(){
        this.used = true;
    }

    public void setNotUsed(){
        this.used = false;
    }

    public String getInitial() {
        return initial;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString(){return this.name;}
}
