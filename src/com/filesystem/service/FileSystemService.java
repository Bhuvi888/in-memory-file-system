package com.filesystem.service;

import com.filesystem.model.*;



public class FileSystemService {
    private Directory root;
    private Directory current;

    public FileSystemService(){
        root = new Directory("/",null);
        current = root;
    }

    public String pwd(){

        StringBuilder path = new StringBuilder();
        Directory temp = current;

        while(temp != null){
            if(temp.getParent() == null){
                path.insert(0,"/");
            }else{
                path.insert(0,"/"+temp.getName());
            }
            temp = temp.getParent();
        }

        return path.toString();

    }

    public void mkdir(String name){
        Directory dir = new Directory(name,current);
        current.add(dir);
    }
    public void touch(String name){
        FileNode file = new FileNode(name,current);
        current.add(file);
    }
    public void ls(){
        for(Node node: current.list()){
            System.out.println(node.getName());
        }
    }
    public void cd(String name){
        if(name.equals("..")){
            if(current.getParent() != null){
                current = current.getParent();
            }
            return;
        }

        Node node = current.get(name);
        if(node != null && node.isDirectory()){
            current = (Directory) node;
        }else{
            System.out.println("Directory not Found");
        }
    }
}
