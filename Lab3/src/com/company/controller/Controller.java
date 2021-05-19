package com.company.controller;

import com.company.model.Pair;
import com.company.repository.Repository;
import com.company.validator.ControllerValidator;
import com.company.validator.ValidatorException;

import java.util.Set;

public class Controller {
    private final Repository repository;
    private final ControllerValidator validator;

    public Controller(Repository repository, ControllerValidator validator) {
        this.validator = validator;
        this.repository = repository;
    }

    public void addVertex(int vertex)throws ValidatorException {
        this.repository.addVertex(vertex);
    }

    public void addEdge(int vertex1, int vertex2, int cost) throws ValidatorException{
        this.repository.addEdge(vertex1,vertex2,cost);
    }

    public int getNumberOfVertices() {
        return this.repository.getNumberOfVertices();
    }

    public int getInDegreeOfVertex(int vertex) throws ValidatorException{
        return this.repository.getInDegreeOfVertex(vertex);
    }

    public int getOutDegreeOfVertex(int vertex) throws ValidatorException{
        return this.repository.getOutDegreeOfVertex(vertex);
    }

    public Set<Integer> getInboundEdgesOfVertex(int vertex) throws ValidatorException{
        return this.repository.getInboundEdgesOfVertex(vertex);
    }

    public Set<Integer> getOutboundEdgesOfVertex(int vertex) throws ValidatorException{
        return this.repository.getOutBoundOfVertex(vertex);
    }

    public Set<Integer> getAllVertices() throws ValidatorException{
        return this.repository.getAllVertices();
    }

    public Set<Pair> getEdgeByVertices(int vertex1, int vertex2) throws ValidatorException{
        return this.repository.getEdgeByVertices(vertex1,vertex2);
    }

    public int getCostOfEdge(int source, int target) throws ValidatorException{
        return this.repository.getCostOfEdge(source,target);
    }

    public void removeEdge(int source, int target) throws ValidatorException{
        this.repository.removeEdge(source,target);
    }

    public void removeVertex(int vertex) throws ValidatorException{
        this.repository.removeVertex(vertex);
    }

    public void changeCostOfAnEdge(int vertex1, int vertex2, int cost) throws ValidatorException{
        this.repository.changeCostOfAnEdge(vertex1,vertex2,cost);
    }

    public String backwardBFS(int source, int target){
        return this.repository.backwardBFS(source,target);
    }

    public String lowestCost(int source, int target){return this.repository.lowestPath(source, target);}
}
