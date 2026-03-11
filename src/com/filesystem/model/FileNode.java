package com.filesystem.model;

public class FileNode extends Node {
    private String content = "";

    public FileNode(String name, Directory parent) {
        super(name, parent);
        this.permission = Permission.defaultFilePermission();
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
