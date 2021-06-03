package com.company.model;

import java.util.ArrayList;

public class MyQueue {

    private ArrayList<Integer> queue;

    /**
     * This mimics a queue.
     */
    public MyQueue() {
        this.queue = new ArrayList<>();
    }

    public boolean isEmpty(){
        return this.queue.size() == 0;
    }

    public void put(int element){
        int end = this.queue.size();
        this.queue.add(end,element);
    }

    public boolean containsValue(int element){
        return this.queue.contains(element);
    }

    public int pop(){
        int element = this.queue.get(0);
        this.queue.remove(0);
        return element;
    }

    public boolean contains(int element){
        return this.queue.contains(element);
    }

    @Override
    public String toString() {
        return "MyQueue{" +
                "queue=" + queue +
                '}';
    }
}
