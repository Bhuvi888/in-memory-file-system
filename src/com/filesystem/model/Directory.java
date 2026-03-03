package com.filesystem.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Directory extends Node {
    private Map<String,Node> children = new HashMap<>();

    public Directory(String name,Directory parent){
        super(name,parent);
    }
    @Override
    public boolean isDirectory() {
        return true;
    }

    public void add(Node node){
        children.put(node.getName(),node);
    }
    public Node get(String name){
        return children.get(name);
    }
    public Collection<com.filesystem.model.Node> list(){
        return children.values();
    }
}
