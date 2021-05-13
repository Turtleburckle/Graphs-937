package com.company;

import com.company.controller.Controller;
import com.company.repository.FileRepository;
import com.company.repository.Repository;
import com.company.ui.Console;
import com.company.validator.ControllerValidator;
import com.company.validator.RepositoryValidator;
import com.company.validator.Validator;
import com.company.validator.ValidatorException;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String fileName = getFilename();
        Validator validator = new Validator();
        ControllerValidator controllerValidator = new ControllerValidator();
        RepositoryValidator repositoryValidator = new RepositoryValidator();
        Repository repository = null;
        if (fileName.contains("random")){
            int numberOfVertices = getNumberOfVertices();
            int numberOfEdges = getNumberOfEdges();
            if(numberOfVertices * numberOfVertices < numberOfEdges){
                numberOfEdges = numberOfVertices * numberOfVertices;
            }
            repository = new FileRepository(repositoryValidator, fileName, numberOfVertices,numberOfEdges);
        }
        else{
            repository = new FileRepository(fileName, repositoryValidator);
        }
        Controller controller = new Controller(repository, controllerValidator);
        Console console = new Console(controller,validator);
        console.run();
    }

    private static int getNumberOfEdges() {
        System.out.println("-> Number of edges: ");
        Validator validator = new Validator();
        Scanner readObject = new Scanner(System.in);
        int numberOfEdges = 0;
        try{
            numberOfEdges = validator.validateNumberOfEdges(readObject.nextLine());
        } catch (ValidatorException exception){
            System.out.println(exception.getMessage());
        }
        return numberOfEdges;
    }

    private static int getNumberOfVertices() {
        System.out.println("-> Number of vertices: ");
        Validator validator = new Validator();
        Scanner readObject = new Scanner(System.in);
        int numberOfVertices = 0;
        try{
            numberOfVertices = validator.validateNumberOfVertices(readObject.nextLine());
        } catch (ValidatorException exception){
            System.out.println(exception.getMessage());
        }
        return numberOfVertices;
    }

    public static String getFilename(){
        System.out.println("______File______");
        System.out.println("1. Example Graph.");
        System.out.println("2. Random Graph.");
        System.out.println("3. Graph 1K.");
        System.out.println("4. Graph 1M.");
        System.out.println("5. Graph 10K.");
        System.out.println("6. Graph 100K.");
        System.out.println("7. ExampleBFS.");
        System.out.println(" >> If you choose any other option a random graph will be choose <<");
        Validator validator = new Validator();
        Scanner readObject = new Scanner(System.in);
        try{
            int option = validator.validateOption(readObject.nextLine());
            switch (option){
                case 1:
                    return "data/Example.txt";
                case 3:
                    return "data/graph1k.txt";
                case 4:
                    return "data/graph1m.txt";
                case 5:
                    return "data/graph10k.txt";
                case 6:
                    return "data/graph100k.txt";
                case 7:
                    return "data/ExampleBFS.txt";
                case 8:
                    return "data/ExampleBFS2.txt";
                default:
                    return"data/random_graph2.txt";
            }
        }catch (ValidatorException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }
}
