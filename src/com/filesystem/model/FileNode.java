package com.filesystem.model;



public class FileNode extends Node {
    public FileNode(String name,Directory parent){
        super(name,parent);
    }

    @Override
    public boolean isDirectory() {
        return false;
    }



}
