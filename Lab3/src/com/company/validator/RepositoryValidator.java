package com.company.validator;

import com.company.model.Pair;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class RepositoryValidator {
    public RepositoryValidator() {
    }

    /**
     * Checks if the vertex doesn't exist in the list of vertices. (doesn't need to exist)
     *
     * @param vertex - the vertex that needs to be checked
     *
     * @param vertices - the list in which the search is made
     *
     * @throws ValidatorException - if the vertex is already in the list
     */
    public void existVertexBad(int vertex, ArrayList<Integer> vertices) throws ValidatorException{
        if(vertices.size() != 0){
            if(vertices.contains(vertex)){throw new ValidatorException("The vertex already exists!");}
        }
    }

    /**
     * Checks if the vertex exists in the list of vertices. (needs to exists)
     *
     * @param vertex - the vertex that needs to be checked
     *
     * @param vertices - the list in which the search is done
     *
     * @throws ValidatorException - if the vertex doesn't exist in the vertices
     */
    public void existVertexGood(int vertex, ArrayList<Integer> vertices) throws ValidatorException{
        if(vertices.size() == 0){
            throw new ValidatorException("There are no vertices!");
        }else if (!vertices.contains(vertex)){ throw new ValidatorException("This vertex doesn't exist!");}
    }

    /**
     * Checks if the edge exists in the dictionary of the Graph.
     *
     * @param vertex1 - the source
     *
     * @param vertex2 - the the target
     *
     * @param dictionary - the dictionary in which we search (can be In or Out)
     *
     * @throws ValidatorException - if the edge already exists
     */
    public void existEdgeDictionaryBad(int vertex1, int vertex2, Map<Integer, ArrayList<Integer>> dictionary, String type) throws ValidatorException{
        if(dictionary.size() != 0){
            if (type.equals("In")){
                if(dictionary.containsKey(vertex2)){
                    if (dictionary.get(vertex2).contains(vertex1)){
                        throw new ValidatorException("This edge already exists!");
                    }
                }
            }else{
                if(dictionary.containsKey(vertex1)){
                    if(dictionary.get(vertex1).contains(vertex2)){throw new ValidatorException("This edge already exists!");}
                }
            }

        }
    }

    /**
     * Checks if the edge exists in the dictionary of the Graph.
     *
     * @param vertex1 - the source
     *
     * @param vertex2 - the the target
     *
     * @param dictionary - the dictionary in which we search (can be In or Out)
     *
     * @throws ValidatorException - if the edge doesn't exists
     */
    public void existEdgeDictionaryGood(int vertex1, int vertex2, Map<Integer, ArrayList<Integer>> dictionary, String type) throws ValidatorException{
        if(dictionary.size() != 0){
            if (type.equals("In")){
                if(dictionary.containsKey(vertex2)){
                    if (!dictionary.get(vertex2).contains(vertex1)){
                        throw new ValidatorException("This edge doesn't exists!");
                    }
                }
            }else{
                if(!dictionary.containsKey(vertex1)){
                    if(dictionary.get(vertex1).contains(vertex2)){throw new ValidatorException("This edge doesn't exists!");}
                }
            }

        }
    }

    /**
     * Checks if the edge exists in the dictionaryCost.
     *
     * @param vertex1 - the source
     *
     * @param vertex2 - the target
     *
     * @param cost - the cost of the edge
     *
     * @param dictionaryCost - the dictionary in which the search is made
     *
     * @throws ValidatorException - if the edge already exists in the dictionary
     */
    public void existEdgeDcostBad(int vertex1, int vertex2,int cost, Map<Pair, Integer> dictionaryCost) throws ValidatorException{
        Pair myPair = new Pair(vertex1,vertex2);
        if (dictionaryCost.containsKey(myPair)){
            if (dictionaryCost.get(myPair) == cost){throw new ValidatorException("This edge is already added!");}
        }
    }

    /**
     * Checks if there is an edge between the vertex and another one (if it's accessible or not).
     *
     * @param vertex - the vertex that we need to check if has connections;
     *
     * @param dictionary - the dictionary we look into;
     *
     * @throws ValidatorException - if the vertex is inaccessible or if the dictionary is empty;
     */
    public void isInaccessible(int vertex, Map<Integer, ArrayList<Integer>> dictionary) throws ValidatorException{
        if(dictionary.size() != 0){
            if(!dictionary.containsKey(vertex)){
                throw new ValidatorException("The vertex is inaccessible!");
            }
        }else{ throw new ValidatorException("There are no edges!");}
    }

    public void existsVertices(ArrayList<Integer> vertices) throws ValidatorException{
        if(vertices.size() == 0){throw new ValidatorException("There are no vertices existent!");}
    }
}
