package com.filesystem.model;

public abstract class Node {
    protected String name;
    protected Directory parent;
    protected Permission permission;

    public Node(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Directory getParent() {
        return this.parent;
    }

    public Permission getPermission() {
        return this.permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public abstract boolean isDirectory();
}
