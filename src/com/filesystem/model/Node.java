package com.filesystem.model;

public abstract class Node {
    protected String name;
    protected Directory parent;

    public Node(String name,Directory parent){
        this.name = name;
        this.parent = parent;
    }

    public String getName(){
        return this.name;
    }
    public Directory getParent(){
        return this.parent;
    }
    public abstract boolean isDirectory();
}
