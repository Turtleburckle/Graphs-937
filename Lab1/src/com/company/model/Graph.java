package com.company.model;

import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    private Map<Integer, ArrayList<Integer>> dictionaryIn;
    private Map<Integer, ArrayList<Integer>> dictionaryOut;
    private Map<Pair,Integer> dictionaryCost;
    private ArrayList<Integer> vertices;

    public Graph() {
        this.dictionaryIn = new HashMap<>();
        this.dictionaryOut = new HashMap<>();
        this.dictionaryCost = new HashMap<>();
        this.vertices = new ArrayList<>();
    }

    public ArrayList<Integer> getVertices() {
        return vertices;
    }

    public Map<Integer, ArrayList<Integer>> getDictionaryIn() {
        return dictionaryIn;
    }

    public Map<Integer, ArrayList<Integer>> getDictionaryOut() {
        return dictionaryOut;
    }

    public Map<Pair, Integer> getDictionaryCost() {
        return dictionaryCost;
    }

    public void addVertex(int vertex){ this.vertices.add(vertex); }

    public void addEdge(int vertex1, int vertex2, int cost){
        if(this.dictionaryIn.containsKey(vertex2)){
            this.dictionaryIn.get(vertex2).add(vertex1);
        }else{
            ArrayList<Integer> inList = new ArrayList<>();
            inList.add(vertex1);
            this.dictionaryIn.put(vertex2,inList);
        }
        if(this.dictionaryOut.containsKey(vertex1)){
            this.dictionaryOut.get(vertex1).add(vertex2);
        }else{
            ArrayList<Integer> outList = new ArrayList<>();
            outList.add(vertex2);
            this.dictionaryOut.put(vertex1,outList);
        }
        Pair newPair = new Pair(vertex1,vertex2);
        this.getDictionaryCost().put(newPair,cost);
    }

    public void removeVertex(int vertexID){
        this.vertices.remove(vertexID);
        if(this.dictionaryIn.containsKey(vertexID)){
            this.dictionaryIn.remove(vertexID);
        }
        this.dictionaryIn.keySet().forEach(key ->
        { if (this.dictionaryIn.get(key).contains(vertexID)){
            this.dictionaryIn.get(key).remove(vertexID);
        }});
        if(this.dictionaryOut.containsKey(vertexID)){
            this.dictionaryOut.remove(vertexID);
        }
        this.dictionaryOut.keySet().forEach(key ->
        { if (this.dictionaryOut.get(key).contains(vertexID)){
            this.dictionaryOut.get(key).remove(vertexID);
        }});
        Set<Pair> pairs = new HashSet<>();
        for(Pair key : this.dictionaryCost.keySet()){
            if(key.getElement1() == vertexID || key.getElement2() == vertexID){
                pairs.add(key);
            }
        }
        for(Pair key : pairs){this.removeEdgeFromDCost(key);}
    }

    public void removeEdge(int vertex1, int vertex2){
        this.dictionaryIn.get(vertex2).remove(vertex1);
        this.dictionaryOut.get(vertex2).remove(vertex1);
        Set<Pair> pairs = new HashSet<>();
        for(Pair key : this.dictionaryCost.keySet()){
            if(key.getElement1() == vertex1 || key.getElement2() == vertex2){
                pairs.add(key);
            }
        }
        for(Pair key : pairs){this.removeEdgeFromDCost(key);}
    }

    public void changeCostOfEdge(int vertex1, int vertex2, int cost) {
        boolean changed =  false;
        this.dictionaryCost.keySet().forEach(key->{
            if (key.getElement1() == vertex1 && key.getElement2() == vertex2){
                this.dictionaryCost.replace(key,cost);
            }
        });
    }

    public void removeEdgeFromDCost(Pair pair){
        this.dictionaryCost.remove(pair);
    }
}
