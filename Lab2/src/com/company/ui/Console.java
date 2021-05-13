package com.company.ui;

import com.company.controller.Controller;
import com.company.model.Pair;
import com.company.validator.Validator;
import com.company.validator.ValidatorException;

import java.util.Scanner;
import java.util.Set;

public class Console {
    private Controller controller;
    private Validator validator;
    private Scanner readObject;

    public Console(Controller controller, Validator validator) {
        this.controller = controller;
        this.validator = validator;
        this.readObject = new Scanner(System.in);
    }

    public void run(){
        while (true) {
            this.printable();
            System.out.println("Please enter your option :");
            try {
                int option = this.validator.validateOption(this.readObject.nextLine());
                switch (option) {
                    case 1:
                        this.mainAdd();
                        break;
                    case 2:
                        this.mainList();
                        break;
                    case 3:
                        this.mainRemove();
                        break;
                    case 4 :
                        this.modifyCostOfEdge();
                        break;
                    case 5 :
                        this.backwardBFS();
                        break;
                    case 0:
                        System.exit(0);
                    default:
                        System.out.println("Invalid option. (range -> 0-5)");
                        break;
                }
            } catch (ValidatorException exception){
                System.out.println(exception.getMessage());
            }
        }
    }

    private void mainAdd(){
        this.printableAdd();
        try{
            int option = this.validator.validateOption(this.readObject.nextLine());
            switch (option){
                case 1 :
                    this.addVertex();
                    break;
                case 2 :
                    this.addEdge();
                    break;
                case 0:
                    this.run();
                    break;
                default:
                    System.out.println("Invalid option! (range -> 0-2)");
                    this.mainAdd();
                    break;
            }
        }catch (ValidatorException exception){ System.out.println(exception.getMessage()); }
    }

    private void addVertex(){
        System.out.println("-> Please enter the vertex ID:");
        try{
            int vertex = this.validator.validateVertexInput(this.readObject.nextLine());
            this.controller.addVertex(vertex);
            System.out.println("Vertex " + vertex + " was successfully added!");
        }catch (ValidatorException exception){
            System.out.println(exception.getMessage());
            System.out.println("Vertex was not added!");
        }
    }

    private void addEdge(){
        try{
            System.out.println("-> Start vertex: ");
            int vertex1 = this.validator.validateVertexInput(this.readObject.nextLine());
            System.out.println("-> End vertex: ");
            int vertex2 = this.validator.validateVertexInput(this.readObject.nextLine());
            System.out.println("-> Cost: ");
            int cost = this.validator.validateCostInput(this.readObject.nextLine());
            this.controller.addEdge(vertex1,vertex2,cost);
            System.out.println("The edge [" + vertex1 + ";" + vertex2 + " | " + cost + " ] was added successfully!");
        }catch (ValidatorException exception){
            System.out.println(exception.getMessage());
            System.out.println("Edge was not added!");
        }
    }

    private void mainList(){
        this.printableList();
        try {
            int option = this.validator.validateOption(this.readObject.nextLine());
            switch (option) {
                case 1:
                    this.listNumberOfVertices();
                    break;
                case 2:
                    this.listDegreeOfVertex("In");
                    break;
                case 3:
                    this.listDegreeOfVertex("Out");
                    break;
                case 4:
                    this.listSetEdgesOfVertex("Inbound");
                    break;
                case 5:
                    this.listSetEdgesOfVertex("Outbound");
                    break;
                case 6:
                    this.listAllVertices();
                    break;
                case 7:
                    this.listEdgeByVertices();
                    break;
                case 8:
                    this.listCostOfEdge();
                    break;
                case 0:
                    this.run();
                    break;
                default:
                    System.out.println("Invalid option! (range -> 0-8)");
                    this.mainList();
                    break;
            }
        }catch (ValidatorException exception){
            System.out.println(exception.getMessage());
        }
    }

    private void listNumberOfVertices(){
        int result = this.controller.getNumberOfVertices();
        switch (result){
            case 0 :
                System.out.println("There are no vertices!");
                break;
            case 1:
                System.out.println("There is 1 vertex!");
                break;
            default:
                System.out.println("There are " + result + " vertices!");
        }

    }

    private void listDegreeOfVertex(String option){
        System.out.println("-> Vertex: ");
        try {
            int vertex = this.validator.validateVertexInput(this.readObject.nextLine());
            switch (option) {
                case "In":
                    int resultIn = this.controller.getInDegreeOfVertex(vertex);
                    System.out.println("The In degree of the vertex " + vertex + " is: " + resultIn + ".");
                    break;
                case "Out":
                    int resultOut = this.controller.getOutDegreeOfVertex(vertex);
                    System.out.println("The Out degree of the vertex " + vertex + " is: " + resultOut + ".");
                    break;
            }
        }catch (ValidatorException exception){
            System.out.println(exception.getMessage());
        }
    }

    private void listSetEdgesOfVertex(String option){
        System.out.println("-> Vertex: ");
        try {
            int vertex = this.validator.validateVertexInput(this.readObject.nextLine());
            switch (option) {
                case "Inbound":
                    Set<Integer> resultInbound= this.controller.getInboundEdgesOfVertex(vertex);
                    if(resultInbound.size() == 0){System.out.println("There are no edges that go in");}
                    else{
                        System.out.println("The inbound edges of the vertex " + vertex + " are: ");
                        resultInbound.forEach(vertex2 -> System.out.println("[" + vertex2 + " ; " + vertex + " ]"));
                    }
                    break;
                case "Outbound":
                    Set<Integer> resultOutbound = this.controller.getOutboundEdgesOfVertex(vertex);
                    if(resultOutbound.size() == 0){ System.out.println("There are not edges that go out of vertex " + vertex + "."); }
                    else{
                        System.out.println("The outbound edges of the vertex " + vertex + " are: ");
                        resultOutbound.forEach(vertex2 -> System.out.println("[" + vertex + " ; " + vertex2 + " ]"));
                    }
                    break;
            }
        }catch (ValidatorException validatorException){
            System.out.println(validatorException);
        }
    }

    private void listAllVertices(){
        try{
            System.out.println("The vertices are :");
            this.controller.getAllVertices().forEach(vertex -> System.out.println(vertex + " "));
        }catch (ValidatorException exception){
            System.out.println(exception.getMessage());
        }
    }

    private void listEdgeByVertices(){
        try{
            System.out.println("-> Vertex 1: ");
            int vertex1 =  this.validator.validateVertexInput(this.readObject.nextLine());
            System.out.println("-> Vertex 2: ");
            int vertex2 = this.validator.validateVertexInput(this.readObject.nextLine());
            Set<Pair> result = this.controller.getEdgeByVertices(vertex1,vertex2);
            if(result.size() == 0){
                System.out.println("There are no edges containing " + vertex1 + " and " + vertex2 + ".");
            } else{
                System.out.println("The edges that contain the vertices " + vertex1  + " and " + vertex2 + " are: ");
                result.forEach(pair -> System.out.println(pair.toString() + " "));
            }
        }catch (ValidatorException exception){
            System.out.println(exception.getMessage());
        }
    }

    private void listCostOfEdge(){
        try{
            System.out.println("-> Source: ");
            int source = this.validator.validateVertexInput(this.readObject.nextLine());
            System.out.println("-> Target: ");
            int target = this.validator.validateVertexInput(this.readObject.nextLine());
            int cost = this.controller.getCostOfEdge(source,target);
            System.out.println("The cost of edge [" + source + " ; " + target + " ] is: " + cost);
        }catch (ValidatorException exception){
            System.out.println(exception.getMessage());
        }
    }

    private void mainRemove(){
        this.printableRemove();
        try {
            int option = this.validator.validateOption(this.readObject.nextLine());
            switch (option) {
                case 1:
                    this.removeVertex();
                    break;
                case 2:
                    this.removeEdge();
                    break;
                case 0:
                    this.run();
                    break;
                default:
                    System.out.println("Invalid option! (range 0-2)");
                    this.mainRemove();
                    break;
            }
        }catch (ValidatorException exception){ System.out.println(exception.getMessage());}
    }

    private void removeEdge() {
        try{
            System.out.println("-> Source: ");
            int source = this.validator.validateVertexInput(this.readObject.nextLine());
            System.out.println("-> Target: ");
            int target = this.validator.validateVertexInput(this.readObject.nextLine());
            this.controller.removeEdge(source,target);
            System.out.println("The edge [ "  + source + " ; " + target + " ] was successfully deleted!");
        }catch (ValidatorException exception){
            System.out.println(exception.getMessage());
        }
    }

    private void removeVertex() {
        try{
            System.out.println("-> Vertex: ");
            int vertex = this.validator.validateVertexInput(this.readObject.nextLine());
            this.controller.removeVertex(vertex);
            System.out.println("The vertex " + vertex + " was successfully deleted!");
        }catch (ValidatorException exception){
            System.out.println(exception.getMessage());
        }
    }

    private void modifyCostOfEdge() {
        try{
            System.out.println("-> Source: ");
            int vertex1 = this.validator.validateVertexInput(this.readObject.nextLine());
            System.out.println("-> Target: ");
            int vertex2 = this.validator.validateVertexInput(this.readObject.nextLine());
            System.out.println("-> Cost:");
            int cost = this.validator.validateCostInput(this.readObject.nextLine());
            this.controller.changeCostOfAnEdge(vertex1,vertex2,cost);
            System.out.println("The change was successfully done!");
        }catch(ValidatorException exception){
            System.out.println("The change wasn't made!");
            System.out.println(exception.getMessage());
        }
    }

    private void backwardBFS(){
        try{
            System.out.println("-> Source: ");
            int vertex1 = this.validator.validateVertexInput(this.readObject.nextLine());
            System.out.println("-> Target : ");
            int vertex2 = this.validator.validateVertexInput(this.readObject.nextLine());
            System.out.println(this.controller.backwardBFS(vertex1, vertex2));
        }catch (ValidatorException exception){
            System.out.println(exception.getMessage());
        }
    }

    private void printableRemove(){
        System.out.println("______Remove______");
        System.out.println("1. Remove Vertex.");
        System.out.println("2. Remove Edge.");
        System.out.println("0. BACK.");
        System.out.println("--> Option:");
    }

    private void printableList(){
        System.out.println("______List______");
        System.out.println("1. List number of vertices.");
        System.out.println("2. List in degree of an Vertex.");
        System.out.println("3. List out degree of an Vertex.");
        System.out.println("4. List set of inbound edges of a vertex.");
        System.out.println("5. List set of outbound edges of a vertex.");
        System.out.println("6. List all vertices.");
        System.out.println("7. List edge by vertices.");
        System.out.println("8. List cost of an edge.");
        System.out.println("0. BACK.");
        System.out.println("--> Option:");
    }

    private void printableAdd(){
        System.out.println("______Add______");
        System.out.println("1. Add Vertex.");
        System.out.println("2. Add Edge.");
        System.out.println("0. BACK.");
        System.out.println("--> Option:");
    }

    private void printable(){
        System.out.println("____Directed Graph____");
        System.out.println("Options :");
        System.out.println("1. Add.");
        System.out.println("2. List.");
        System.out.println("3. Remove.");
        System.out.println("4. Modify cost of edge.");
        System.out.println("5. Backward BFS");
        System.out.println("0. EXIT.");
        System.out.println("--> Option:");
    }
}
