package com.company.model;

import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    private Map<Integer, ArrayList<Integer>> dictionaryIn;
    private Map<Integer, ArrayList<Integer>> dictionaryOut;
    private Map<Pair,Integer> dictionaryCost;
    private ArrayList<Integer> vertices;
    private ArrayList<Integer> topologicalSorting;
    private ArrayList<Pair> undirectedGraph;
    private ArrayList<Color> colors;

    public Graph() {
        this.dictionaryIn = new HashMap<>();
        this.dictionaryOut = new HashMap<>();
        this.dictionaryCost = new HashMap<>();
        this.vertices = new ArrayList<>();
        this.topologicalSorting = new ArrayList<>();
        this.undirectedGraph = this.generateUndirectedGraph();
        this.colors = new ArrayList<>();
    }

    private ArrayList<Pair> generateUndirectedGraph(){
        return new ArrayList<>(this.dictionaryCost.keySet());
    }

    public void addColor(String name, String initial){
        Color color = new Color(name,initial,this.colors.size());
        this.colors.add(color);
    }

    private boolean existsEdgeBetweenTwoVertices(int vertex1, int vertex2){
        for(Pair pair : this.dictionaryCost.keySet()){
            if(pair.getElement1() == vertex1 && pair.getElement2() == vertex2){
                return true;
            }else if(pair.getElement1() == vertex2 && pair.getElement2() == vertex1){
                return true;
            }
        }
        return false;
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

    /**
     * Returns the result of the BFS if it's the case.
     *
     * @param source - the start point
     * @param target - the end point
     * @return - the string result of the path or the corresponding message if it's the case.
     */
    public String backwardBFS(int source, int target) {
        if(this.dictionaryOut.containsKey(source) && this.dictionaryIn.containsKey(target)){
            Path bestPath = this.getBackwardPathBFS(source, target);
            if (bestPath != null){
                bestPath.setWay(-1);
                return bestPath.toString();
            }else {return "No path was found!";}
        }else{return "The source or the target can't be reached!";}
    }


    /**
     * Generates the path backwardly using BFS.
     *
     * @param source - the start point
     * @param target - the end point
     * @return - the best path it founds
     */
    private Path getBackwardPathBFS(int source, int target) {
       MyQueue queue = new MyQueue();
       ArrayList<Path> paths = new ArrayList<>();
       ArrayList<Integer> alreadyChecked = new ArrayList<>();
       queue.put(target);

       int currentNode = -1;
       Path firstPath = new Path();
       firstPath.addToPath(target,0);
       paths.add(firstPath);
       alreadyChecked.add(target);

       while(!queue.isEmpty()){
           currentNode = queue.pop();
            ArrayList<Path> pathsToAdd = new ArrayList<>();
            ArrayList<Path> pathsToDelete = new ArrayList<>();
            if(this.dictionaryIn.keySet().contains(currentNode)) {
                for (int neighbor : this.dictionaryIn.get(currentNode)) {
                    if(!alreadyChecked.contains(neighbor)) {
                        for (Path path : paths) {
                            if (path.lastElementCheck(currentNode)) {
                                Path newPath = new Path(path);
                                newPath.addToPath(neighbor, this.getCostOfEdge(neighbor, currentNode));
                                    pathsToAdd.add(newPath);
                                if (!pathsToDelete.contains(path)) {
                                    pathsToDelete.add(path);
                                }
                            }
                        }
                   }
                    if (!alreadyChecked.contains(neighbor) && !queue.containsValue(neighbor)) {
                        queue.put(neighbor);
                    }
                }
            }
           alreadyChecked.add(currentNode);
           paths.addAll(pathsToAdd);
           paths.removeAll(pathsToDelete);
       }

       Path bestPath = paths.get(0);
       int shortestLength = 999;

       for(Path path : paths){
           if(path.lastElementCheck(source)){
               if(shortestLength > path.getLength()){
                   bestPath = path;
                   shortestLength = path.getLength();
               }
           }
       }
       if(shortestLength == 999){return null;}
       return bestPath;
    }

    private int getCostOfEdge(int source, int target){
        for(Pair pair : this.dictionaryCost.keySet()){
            if(pair.getElement1() == source && pair.getElement2() == target){return this.dictionaryCost.get(pair);}}
        return -1;
    }

    public String getLowestPath(int source, int target){
        return this.BellmanFord(source,target);
    }

    private String BellmanFord(int source, int target){
        //Step 1
        ArrayList<Double> distance = new ArrayList<Double>();
        ArrayList<Integer> predecessor = new ArrayList<Integer>();
        double inf = Double.POSITIVE_INFINITY;
        for(int vertex : this.vertices){
            distance.add(inf);
            predecessor.add(null);
        }

        distance.set(source, 0d);

        //Step 2
        for(int index = 0; index < this.vertices.size()-1; index++){
            for(Pair edge : this.dictionaryCost.keySet()){
                if(distance.get(edge.getElement1()) + this.dictionaryCost.get(edge) < distance.get(edge.getElement2())){
                    distance.set(edge.getElement2(), distance.get(edge.getElement1()) + this.dictionaryCost.get(edge));
                    predecessor.set(edge.getElement2(), edge.getElement1());
                }
            }
        }

        //Step 3
        for(Pair edge : this.dictionaryCost.keySet()){
            if(distance.get(edge.getElement1()) + this.dictionaryCost.get(edge) < distance.get(edge.getElement2())){
                return "Negative Cycle!";
            }
        }

        int current = target;
        ArrayList<Integer> path = new ArrayList<>();

        while(current != source){
            path.add(current);
            current = predecessor.get(current);
        }

        String pathString = "";
        pathString += "Lowest Path = { " + source + " -> ";

        for(int index = path.size() - 1; index >=  0; index--){
            if (index != 0){
                pathString += path.get(index) + " -> ";
            }else{
                pathString += path.get(index) + "} cost = " +  distance.get(target);
            }
        }

        return pathString;
    }

    public String TopologicalSorting(){
        Map<Integer,Integer> predecessorCounting = new HashMap<>();
        MyQueue queue = new MyQueue();
        ArrayList<Integer> list = new ArrayList<>();

        for(int index = 0; index < this.vertices.size(); index++){
            int currentVertex = this.vertices.get(index);
            if(this.dictionaryIn.keySet().contains(currentVertex)) {
                predecessorCounting.put(currentVertex, this.dictionaryIn.get(currentVertex).size());
            }else{
                predecessorCounting.put(currentVertex, 0);
                queue.put(currentVertex);
            }
        }

        while(!queue.isEmpty()){
            int currentVertex = queue.pop();
            if(this.dictionaryOut.keySet().contains(currentVertex)){
                for(int index = 0; index < this.dictionaryOut.get(currentVertex).size(); index++){
                    int vertex = this.dictionaryOut.get(currentVertex).get(index);
                    predecessorCounting.replace(vertex, predecessorCounting.get(vertex) - 1 );
                }
            }

            list.add(currentVertex);

            for(int vertex : predecessorCounting.keySet()){
                if (predecessorCounting.get(vertex) == 0 && !queue.contains(vertex) && !list.contains(vertex)){
                    queue.put(vertex);
                }
            }
        }

        boolean isDAG = true;
        for(int vertex : predecessorCounting.keySet()){
            if(predecessorCounting.get(vertex) != 0){isDAG = false;}
        }

        if (!isDAG){ return "The Graph is not Acyclic!"; }
        else{
            this.topologicalSorting = list;
            String result = "Topological Sorting of the Graph = { ";
            for(int vertex : list){
                result += vertex + "  ";
            }
            return result + "}";
        }

    }

    public String highestCostPathDAG(int source, int target){

        if(this.topologicalSorting.size() == 0){return "The Graph is not Acyclic!";}

        Double inf = Double.NEGATIVE_INFINITY;
        Map<Integer, Double> distances = new HashMap<>();
        for (int vertex : this.topologicalSorting){
            distances.put(vertex,inf);
        }

        distances.replace(source,0d);

        for(int vertex : this.topologicalSorting){
            if(vertex == target){break;}
            if(this.dictionaryOut.containsKey(vertex)){
                for(int adjacentVertex : this.dictionaryOut.get(vertex)){
                    if(distances.get(adjacentVertex) < distances.get(vertex) + this.getCostOfEdge(vertex,adjacentVertex)){
                        distances.replace(adjacentVertex,distances.get(vertex) + this.getCostOfEdge(vertex,adjacentVertex));
                    }
                }
            }
        }
        if(distances.get(target).equals(inf)){return "NO PATHS EXIST BETWEEN " + source + " AND " + target + "\n";}
        return "Highest Cost Path : " + distances.get(target) + " ";
    }

    public String colorGraphMinimum(){
        ArrayList<Color> colorsUsed = new ArrayList<>();        // a list of all the colors used overall
        ArrayList<ColoredVertex> previous = new ArrayList<>();  // a list of previous nodes that were colored
        ArrayList<ColoredVertex> allNodes = new ArrayList<>();  // a list of all the nodes from the undirected graph

        for(int vertex : this.vertices){                        // adds all the nodes from the list
            ColoredVertex newVertex = new ColoredVertex(vertex);
            allNodes.add(newVertex);
        }

        allNodes.get(0).setColor(this.colors.get(0));
        previous.add(allNodes.get(0));
        allNodes.remove(0);
        colorsUsed.add(this.colors.get(0));

        for(ColoredVertex uncoloredVertex : allNodes){
            ArrayList<Color> copyUsedColors = new ArrayList<>(colorsUsed);  // copy of used color list
            for(ColoredVertex coloredVertex : previous){
                if(this.existsEdgeBetweenTwoVertices(uncoloredVertex.vertex,coloredVertex.vertex)){
                    if(copyUsedColors.contains(coloredVertex.color)){
                        copyUsedColors.remove(coloredVertex.color);
                    }
                }
            }
            if(copyUsedColors.size() != 0){
                uncoloredVertex.setColor(copyUsedColors.get(0));
            }else{
                uncoloredVertex.setColor(this.colors.get(colorsUsed.size()));
                colorsUsed.add(this.colors.get(colorsUsed.size()));
            }
            previous.add(uncoloredVertex);
        }

        String result = " ";
        result += previous.get(0).vertex + " - " + previous.get(0).color + "\n";
        for(ColoredVertex vertex : allNodes){
            result += vertex.vertex + " - " + vertex.color + "\n";
        }
        return result;

    }
}
