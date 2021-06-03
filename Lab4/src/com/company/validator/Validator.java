package com.company.validator;

public class Validator {
    public Validator() {
    }

    public int validateOption(String option) throws ValidatorException{
        if(option.equals("") || option.equals(" ")){
            throw new ValidatorException("The option can't be empty!");
        }
        int intVersion;
        try{
             intVersion = Integer.parseInt(option);
             if(intVersion<0){throw new ValidatorException("The option can't be less than 0!");}
        }catch (Exception exception){throw new ValidatorException("The option must be an integer!");}
        return intVersion;
    }

    public int validateVertexInput(String vertexInput) throws ValidatorException{
        if(vertexInput.equals("") || vertexInput.equals(" ")){
            throw new ValidatorException("The vertex ID can't be empty!");
        }
        int vertexInt;
        try{
            vertexInt = Integer.parseInt(vertexInput);
            if(vertexInt<0){throw new ValidatorException("The vertex can't be negative!");}
        }catch (Exception exception){throw new ValidatorException("The vertex has to be a number!");}
        return vertexInt;
    }

    public int validateCostInput(String costInput) throws ValidatorException{
        if(costInput.equals("") || costInput.equals(" ")){
            throw new ValidatorException("The cost can't be empty!");
        }
        int costInt;
        try{
            costInt = Integer.parseInt(costInput);
        }catch (Exception exception){throw new ValidatorException("The cost must be a number!");}
        return costInt;
    }

    public int validateNumberOfVertices(String numberOfVertices) throws ValidatorException{
        if (numberOfVertices.equals("") || numberOfVertices.contains(" ")){
            throw new ValidatorException("The number of vertices can't be empty!");
        }
        try{
            int intResult = Integer.parseInt(numberOfVertices);
            if(intResult <= 0){throw new ValidatorException("The number must be greater than 0!");}
            else{return intResult;}
        }catch (Exception exception){throw new ValidatorException("The number of vertices must be a number!");}
    }

    public int validateNumberOfEdges(String numberOfEdges) throws ValidatorException{
        if (numberOfEdges.equals("") || numberOfEdges.contains(" ")){
            throw new ValidatorException("The number of edges can't be empty!");
        }
        try{
            int intResult = Integer.parseInt(numberOfEdges);
            if(intResult <= 0){throw new ValidatorException("The number must be greater than 0!");}
            else{return intResult;}
        }catch (Exception exception){throw new ValidatorException("The number of edges must be a number!");}
    }
}
