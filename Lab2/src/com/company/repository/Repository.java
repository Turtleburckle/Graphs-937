package com.company.repository;

import com.company.model.Graph;
import com.company.model.Pair;
import com.company.validator.RepositoryValidator;
import com.company.validator.ValidatorException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Repository {
    private final RepositoryValidator validator;
    private final Graph graph;
    private int numberOfVertices;
    private int numberOfEdges;

    public Repository(RepositoryValidator validator) {
        this.numberOfEdges = 0;
        this.numberOfVertices = 0;
        this.validator = validator;
        this.graph = new Graph();
    }

    /**
     * Checks if the vertex isn't already added and tries to add it.
     *
     * @param vertex - the vertex that needs to be added;
     *
     * @throws ValidatorException - if the vertex is already added;
     */
    public void addVertex(int vertex) throws ValidatorException {
        this.validator.existVertexBad(vertex, this.graph.getVertices());
        this.graph.addVertex(vertex);
        this.numberOfVertices++;
    }

    /**
     * Checks if the vertices exist already, if the edge is not already added and tries to add the edge.
     *
     * @param vertex1 - the source of the edge
     *
     * @param vertex2 - the target of the edge
     *
     * @param cost - the cost of the edge
     *
     * @throws ValidatorException - if the vertices do not exist or if the edge already exists
     */
    public void addEdge(int vertex1, int vertex2, int cost) throws ValidatorException{
        this.validator.existVertexGood(vertex1, this.graph.getVertices());
        this.validator.existVertexGood(vertex2, this.graph.getVertices());
        this.validator.existEdgeDictionaryBad(vertex1,vertex2, this.graph.getDictionaryIn(),"In");
        this.validator.existEdgeDictionaryBad(vertex1,vertex2, this.graph.getDictionaryOut(),"Out");
        this.validator.existEdgeDcostBad(vertex1,vertex2,cost, this.graph.getDictionaryCost());
        this.graph.addEdge(vertex1,vertex2,cost);
        this.numberOfEdges++;
    }

    /**
     * Getter for the number of vertices.
     *
     * @return the number of vertices;
     */
    public int getNumberOfVertices() {
        return numberOfVertices;
    }

    /**
     * Gets the number of edges that have the vertex as target.
     *
     * @param vertex - the vertex we calculate the In Degree for;
     *
     * @return - the In Degree (number of edges that have the vertex as target);
     *
     * @throws ValidatorException - if the vertex doesn't exist or if the vertex is inaccessible
     */
    public int getInDegreeOfVertex(int vertex) throws ValidatorException{
        this.validator.existVertexGood(vertex, this.graph.getVertices());
        this.validator.isInaccessible(vertex,this.graph.getDictionaryIn());
        return this.graph.getDictionaryIn().get(vertex).size();
    }

    /**
     * Gets the number of edges that have the vertex as source.
     *
     * @param vertex - the vertex we calculate the Out Degree for;
     *
     * @return - the Out Degree (number of edges that have the vertex as source);
     *
     * @throws ValidatorException - if the vertex doesn't exist or if the vertex is inaccessible;
     */
    public int getOutDegreeOfVertex(int vertex) throws ValidatorException{
        this.validator.existVertexGood(vertex, this.graph.getVertices());
        this.validator.isInaccessible(vertex,this.graph.getDictionaryOut());
        return this.graph.getDictionaryOut().get(vertex).size();
    }

    /**
     * Gets the inbound edges of the vertex (the edges that have vertex as target).
     *
     * @param vertex - the vertex we take the inbound edges from.
     *
     * @return - the set of sources
     *
     * @throws ValidatorException - if the vertex doesn't exist or if the vertex is inaccessible;
     */
    public Set<Integer> getInboundEdgesOfVertex(int vertex) throws ValidatorException{
        this.validator.existVertexGood(vertex, this.graph.getVertices());
        this.validator.isInaccessible(vertex, this.graph.getDictionaryIn());
        return new HashSet<>(this.graph.getDictionaryIn().get(vertex));
    }

    /**
     * Gets the outbound edges of the vertex (the edges that have vertex as source).
     *
     * @param vertex - the vertex we take the outbound edges from.
     *
     * @return - the set of targets
     *
     * @throws ValidatorException - if the vertex doesn't exist or if the vertex is inaccessible;
     */
    public Set<Integer> getOutBoundOfVertex(int vertex) throws ValidatorException{
        this.validator.existVertexGood(vertex, this.graph.getVertices());
        this.validator.isInaccessible(vertex, this.graph.getDictionaryOut());
        return new HashSet<>(this.graph.getDictionaryOut().get(vertex));
    }

    public Set<Integer> getAllVertices() throws ValidatorException{
        this.validator.existsVertices(this.graph.getVertices());
        return this.graph.getVertices().stream().collect(Collectors.toSet());
    }

    /**
     * Gets all the edges that are formed by vertices vertex1 and vertex2.
     *
     * @param vertex1 - one of the vertices;
     *
     * @param vertex2 - the other vertex;
     *
     * @return - a set of pairs that contains the edges formed by the two vertices (if existent);
     *
     * @throws ValidatorException - if one of the vertices doesn't exist.
     */
    public Set<Pair> getEdgeByVertices(int vertex1, int vertex2) throws ValidatorException{
        this.validator.existVertexGood(vertex1,this.graph.getVertices());
        this.validator.existVertexGood(vertex2,this.graph.getVertices());
        Pair pair12 = new Pair(vertex1,vertex2);
        Pair pair21 = new Pair(vertex2,vertex1);
        Set<Pair> result = new HashSet<>();
        if(this.graph.getDictionaryCost().containsKey(pair12)){result.add(pair12);}
        if(this.graph.getDictionaryCost().containsKey(pair21)){result.add(pair21);}
        return result;
    }

    /**
     * Gets the cost of an edge.
     *
     * @param source - the source of the edge;
     *
     * @param target - the target of the edge;
     *
     * @return - the cost of the edge formed by the two vertices;
     *
     * @throws ValidatorException - if one of the vertices doesn't exist or if the edge doesn't exist;
     */
    public int getCostOfEdge(int source, int target) throws ValidatorException{
        this.validator.existVertexGood(source,this.graph.getVertices());
        this.validator.existVertexGood(target,this.graph.getVertices());
        this.validator.existEdgeDictionaryGood(source,target,this.graph.getDictionaryIn(),"In");
        for(Pair key : this.graph.getDictionaryCost().keySet()){
            if (key.getElement1() == source && key.getElement2() == target){return this.graph.getDictionaryCost().get(key);}
        }
        return 0;
    }

    /**
     * Removes a vertex from the graph.
     *
     * @param vertex - the vertex that needs to be removed;
     *
     * @throws ValidatorException - if the vertex doesn't exist;
     */
    public void removeVertex(int vertex) throws ValidatorException{
        this.validator.existVertexGood(vertex, this.graph.getVertices());
        this.graph.removeVertex(vertex);
        this.numberOfVertices--;
    }

    /**
     * Removes an edge from the graph.
     *
     * @param vertex1 - the source of the edge;
     *
     * @param vertex2 - the target of the edge;
     *
     * @throws ValidatorException - if one of the vertices doesn't exist or if the edge doesn't exist;
     */
    public void removeEdge(int vertex1, int vertex2) throws ValidatorException{
        this.validator.existVertexGood(vertex1, this.graph.getVertices());
        this.validator.existVertexGood(vertex2, this.graph.getVertices());
        this.validator.existEdgeDictionaryGood(vertex1, vertex2, this.graph.getDictionaryIn(),"In");
        this.graph.removeEdge(vertex1,vertex2);
        this.numberOfEdges = this.graph.getDictionaryCost().size();
    }

    /**
     * Changes the cost of an existent edge.
     *
     * @param vertex1 - the source of the edge;
     *
     * @param vertex2 - the target of the edge;
     *
     * @param cost - the cost that we change with;
     *
     * @throws ValidatorException - if one of the vertices doesn't exist or if the edge doesn't exist;
     */
    public void changeCostOfAnEdge(int vertex1, int vertex2, int cost) throws ValidatorException{
        this.validator.existVertexGood(vertex1,this.graph.getVertices());
        this.validator.existVertexGood(vertex2,this.graph.getVertices());
        this.validator.existEdgeDictionaryGood(vertex1,vertex2,this.graph.getDictionaryIn(),"In");
        this.graph.changeCostOfEdge(vertex1,vertex2,cost);
    }

    public String backwardBFS(int source, int target) {
        this.validator.existVertexGood(source, this.graph.getVertices());
        this.validator.existVertexGood(target, this.graph.getVertices());
        return this.graph.backwardBFS(source,target);
    }

    /**
     * Setter for the number of Vertices.
     *
     * @param numberOfVertices - the number of vertices;
     */
    public void setNumberOfVertices(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
    }


    /**
     * Setter for the number of Edges.
     *
     * @param numberOfEdges - the number of edges;
     */
    public void setNumberOfEdges(int numberOfEdges) {
        this.numberOfEdges = numberOfEdges;
    }

    /**
     * Getter for the number of Edges.
     *
     * @return - the number of edges;
     */
    public int getNumberOfEdges() {
        return numberOfEdges;
    }

    /**
     * Checks if a vertex exists in the graph.
     *
     * @param vertex - the vertex we check if exists in the graph.
     *
     * @return - true if exists, false otherwise.
     */
    protected boolean existsVertex(int vertex){
        return this.graph.getVertices().contains(vertex);
    }

    /**
     * Checks if an edge exists in the Graph.
     *
     * @param source - the source of the edge;
     *
     * @param target - the target of the edge;
     *
     * @return - true if it exist, false otherwise;
     */
    protected boolean existsEdge(int source, int target) {
        if(this.graph.getDictionaryIn().containsKey(target)){
            return this.graph.getDictionaryIn().get(target).contains(source);
        }
        return false;
    }


    protected void addVertexFromFile(int vertex){
        if (!this.existsVertex(vertex)){this.graph.addVertex(vertex);}
    }

    protected void addEdgeFromFile(int source, int target, int cost){
        if(!this.existsEdge(source,target)){this.graph.addEdge(source,target,cost);}
    }

    protected void checkMissing(){
        ArrayList<Integer> vertices = this.graph.getVertices();
        if (this.numberOfVertices != vertices.size()){
            for(int index=0; index < this.numberOfVertices; index++){
                if(!vertices.contains(index)){this.graph.addVertex(index);}
            }
        }
    }

    protected Map<Pair, Integer> getDictionaryCost(){
        return this.graph.getDictionaryCost();
    }
}
