package com.company.model;

import java.util.ArrayList;

public class Path {

    private ArrayList<Integer> path;
    private int length;
    private int way;

    /**
     * Generates an array which represents a path, having a length (which is the sum of all costs between the edges),
     * the way which can be -1 for backwards and normal otherwise (just for print purposes).
     */
    public Path() {
        this.path = new ArrayList<>();
        this.length = 0;
        this.way = 0;
    }

    public Path(Path oldPath){
        this.path = new ArrayList<>();
        this.path.addAll(oldPath.getPath());
        this.length = oldPath.getLength();
        this.way = oldPath.getWay();
    }

    public int getLength() {
        return length;
    }

    public int getSize(){
        return this.path.size();
    }

    public ArrayList<Integer> getPath() {
        return path;
    }

    public int getWay() {
        return way;
    }

    public void setWay(int way) {
        this.way = way;
    }

    public void addToPath(int element, int lengthBetween){
        this.path.add(element);
        this.length += lengthBetween;
    }

    public int getLastElement(){
        return this.path.get(this.getSize()-1);
    }

    public boolean lastElementCheck(int element){
        return this.getLastElement() == element;
    }

    @Override
    public String toString() {
        String output = "";
        String sign = "";
        if(this.way == -1){
            sign = " <- ";
        }else {sign = " -> ";}
        boolean first = true;
        for(int node : this.path){
            if(first){
                first = false;
                output += "" + node;
            }else {output += sign + node;}
        }
        if (this.way == -1 ){return "Path{ Target: " + output + " :Source} Length: " + this.length + "";}
        else{return "Path{ Source: " + output + " :Target} Length: " + this.length + "";}

    }
}
