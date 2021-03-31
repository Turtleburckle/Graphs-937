package com.company.repository;

import com.company.model.Pair;
import com.company.validator.RepositoryValidator;
import com.company.validator.ValidatorException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class FileRepository extends Repository{
    private String fileName;

    public FileRepository(String fileName, RepositoryValidator validator) {
        super(validator);
        this.fileName = fileName;
        this.loadData();
    }

    public FileRepository(RepositoryValidator validator, String fileName, int numberOfVertices, int numberOfEdges) {
        super(validator);
        this.fileName = fileName;
        super.setNumberOfVertices(numberOfVertices);
        super.setNumberOfEdges(numberOfEdges);
        this.generateRandomGraph();
    }

    @Override
    public void addVertex(int vertex) throws ValidatorException {
        super.addVertex(vertex);
        this.writeData();
    }

    @Override
    public void addEdge(int vertex1, int vertex2, int cost) throws ValidatorException {
        super.addEdge(vertex1, vertex2, cost);
        this.writeData();
    }

    @Override
    public int getNumberOfVertices() {
        return super.getNumberOfVertices();
    }

    @Override
    public int getInDegreeOfVertex(int vertex) throws ValidatorException {
        return super.getInDegreeOfVertex(vertex);
    }

    @Override
    public int getOutDegreeOfVertex(int vertex) throws ValidatorException {
        return super.getOutDegreeOfVertex(vertex);
    }

    @Override
    public Set<Integer> getInboundEdgesOfVertex(int vertex) throws ValidatorException {
        return super.getInboundEdgesOfVertex(vertex);
    }

    @Override
    public Set<Integer> getOutBoundOfVertex(int vertex) throws ValidatorException {
        return super.getOutBoundOfVertex(vertex);
    }

    @Override
    public Set<Integer> getAllVertices() throws ValidatorException {
        return super.getAllVertices();
    }

    @Override
    public Set<Pair> getEdgeByVertices(int vertex1, int vertex2) throws ValidatorException {
        return super.getEdgeByVertices(vertex1, vertex2);
    }

    @Override
    public int getCostOfEdge(int source, int target) throws ValidatorException {
        return super.getCostOfEdge(source, target);
    }

    @Override
    public void removeVertex(int vertex) throws ValidatorException {
        super.removeVertex(vertex);
        this.writeData();
    }

    @Override
    public void removeEdge(int vertex1, int vertex2) throws ValidatorException {
        super.removeEdge(vertex1, vertex2);
        this.writeData();
    }

    @Override
    public void changeCostOfAnEdge(int vertex1, int vertex2, int cost) throws ValidatorException {
        super.changeCostOfAnEdge(vertex1, vertex2, cost);
        this.writeData();
    }

    private void loadData(){
        Path path = Paths.get(this.fileName);
        AtomicBoolean first = new AtomicBoolean(true);
        try{
            Files.lines(path).forEach(line->{
                List<String> items = Arrays.asList(line.split(" "));
                if(first.get()){
                    super.setNumberOfVertices(Integer.parseInt(items.get(0)));
                    super.setNumberOfEdges(Integer.parseInt(items.get(1)));
                    first.set(false);
                }else{
                    int vertex1 = Integer.parseInt(items.get(0));
                    super.addVertexFromFile(vertex1);
                    int vertex2 = Integer.parseInt(items.get(1));
                    super.addVertexFromFile(vertex2);
                    int cost = Integer.parseInt(items.get(2));
                    super.addEdgeFromFile(vertex1,vertex2,cost);
                }
            });
        }catch(IOException exception){exception.getCause();}
        super.checkMissing();
    }

    public void writeData(){
        if(!this.fileName.contains("random") || this.fileName.contains("Changed")){
            String[] piece = this.fileName.split("\\.");
            this.fileName = piece[0] + "Changed.txt";
        }
        Path path = Paths.get(this.fileName);
        AtomicBoolean first = new AtomicBoolean(true);
        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING)){
            bufferedWriter.write(super.getNumberOfVertices() + " " + super.getNumberOfEdges());
            bufferedWriter.newLine();

            for(Pair keyPair : super.getDictionaryCost().keySet()){
                bufferedWriter.write(keyPair.getElement1() +" "+ keyPair.getElement2() +" "+ super.getDictionaryCost().get(keyPair));
                bufferedWriter.newLine();
            }
        }catch (IOException exception){exception.getCause();}
    }

    private int getRandomVertex(){
        Random random = new Random();
        return random.nextInt(super.getNumberOfVertices());
    }

    private int getRandomCost(){
        Random random = new Random();
        return random.nextInt(100);
    }

    private void generateRandomGraph(){
        for (int index=0; index< super.getNumberOfVertices(); index++){
            super.addVertexFromFile(index);
        }
        while(super.getDictionaryCost().size() < super.getNumberOfEdges()){
            int v1 = this.getRandomVertex();
            int v2 = this.getRandomVertex();
            int cost = this.getRandomCost();
            if(v1 >= 0 && v2 >= 0){super.addEdgeFromFile(v1,v2,cost);}
        }
        this.writeData();
    }
}
