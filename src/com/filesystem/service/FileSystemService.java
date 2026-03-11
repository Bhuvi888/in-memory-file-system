package com.filesystem.service;

import com.filesystem.model.*;

import java.util.ArrayList;
import java.util.List;

public class FileSystemService {
    private Directory root;
    private Directory current;

    public FileSystemService() {
        root = new Directory("/", null);
        current = root;
    }

    public String pwd() {
        if (current == root) {
            return "/";
        }

        StringBuilder path = new StringBuilder();
        Directory temp = current;

        while (temp != null && temp.getParent() != null) {
            path.insert(0, "/" + temp.getName());
            temp = temp.getParent();
        }

        return path.toString();
    }

    public String mkdir(String name) {
        if (name == null || name.isEmpty()) {
            return "Error: directory name required.";
        }
        if (current.get(name) != null) {
            return "Error: '" + name + "' already exists.";
        }
        Directory dir = new Directory(name, current);
        current.add(dir);
        return null;
    }

    public String touch(String name) {
        if (name == null || name.isEmpty()) {
            return "Error: file name required.";
        }
        if (current.get(name) != null) {
            return "Error: '" + name + "' already exists.";
        }
        FileNode file = new FileNode(name, current);
        current.add(file);
        return null;
    }

    public String ls() {
        StringBuilder sb = new StringBuilder();
        for (Node node : current.list()) {
            String type = node.isDirectory() ? "[DIR] " : "[FILE]";
            String perms = node.getPermission().toString();
            sb.append(type).append(" ").append(perms).append("  ").append(node.getName()).append("\n");
        }
        return sb.toString();
    }

    public String cd(String name) {
        if (name == null || name.isEmpty()) {
            return "Error: directory name required.";
        }
        if (name.equals("..")) {
            if (current.getParent() != null) {
                current = current.getParent();
            }
            return null;
        }

        Node node = current.get(name);
        if (node == null) {
            return "Error: '" + name + "' not found.";
        }
        if (!node.isDirectory()) {
            return "Error: '" + name + "' is not a directory.";
        }
        if (!node.getPermission().canExecute()) {
            return "Error: permission denied — no execute permission on '" + name + "'.";
        }
        current = (Directory) node;
        return null;
    }

    public String cat(String name) {
        if (name == null || name.isEmpty()) {
            return "Error: file name required.";
        }
        Node node = current.get(name);
        if (node == null) {
            return "Error: '" + name + "' not found.";
        }
        if (node.isDirectory()) {
            return "Error: '" + name + "' is a directory, not a file.";
        }
        if (!node.getPermission().canRead()) {
            return "Error: permission denied — no read permission on '" + name + "'.";
        }
        return ((FileNode) node).getContent();
    }

    public String write(String name, String content) {
        if (name == null || name.isEmpty()) {
            return "Error: file name required.";
        }
        Node node = current.get(name);
        if (node == null) {
            return "Error: '" + name + "' not found. Use 'touch' to create it first.";
        }
        if (node.isDirectory()) {
            return "Error: '" + name + "' is a directory, not a file.";
        }
        if (!node.getPermission().canWrite()) {
            return "Error: permission denied — no write permission on '" + name + "'.";
        }
        ((FileNode) node).setContent(content);
        return null;
    }

    public String rm(String name) {
        if (name == null || name.isEmpty()) {
            return "Error: name required.";
        }
        Node node = current.get(name);
        if (node == null) {
            return "Error: '" + name + "' not found.";
        }
        if (!current.getPermission().canWrite()) {
            return "Error: permission denied — no write permission on current directory.";
        }
        current.remove(name);
        return null;
    }

    public String mv(String source, String dest) {
        if (source == null || source.isEmpty() || dest == null || dest.isEmpty()) {
            return "Error: usage: mv <source> <destination>";
        }
        Node node = current.get(source);
        if (node == null) {
            return "Error: '" + source + "' not found.";
        }
        if (current.get(dest) != null) {
            return "Error: '" + dest + "' already exists.";
        }
        current.remove(source);

        // Create a renamed copy
        if (node.isDirectory()) {
            Directory oldDir = (Directory) node;
            Directory newDir = new Directory(dest, current);
            for (Node child : oldDir.list()) {
                newDir.add(child);
            }
            current.add(newDir);
        } else {
            FileNode oldFile = (FileNode) node;
            FileNode newFile = new FileNode(dest, current);
            newFile.setContent(oldFile.getContent());
            current.add(newFile);
        }
        return null;
    }

    public String find(String name) {
        if (name == null || name.isEmpty()) {
            return "Error: search name required.";
        }
        List<String> results = new ArrayList<>();
        findRecursive(current, name, pwd(), results);
        if (results.isEmpty()) {
            return "No matches found for '" + name + "'.";
        }
        StringBuilder sb = new StringBuilder();
        for (String path : results) {
            sb.append(path).append("\n");
        }
        return sb.toString();
    }

    private void findRecursive(Directory dir, String name, String currentPath, List<String> results) {
        for (Node node : dir.list()) {
            String nodePath = currentPath.equals("/") ? "/" + node.getName() : currentPath + "/" + node.getName();
            if (node.getName().equals(name)) {
                results.add(nodePath);
            }
            if (node.isDirectory()) {
                findRecursive((Directory) node, name, nodePath, results);
            }
        }
    }

    public String chmod(String perms, String name) {
        if (perms == null || perms.isEmpty() || name == null || name.isEmpty()) {
            return "Error: usage: chmod <permissions> <name>  (e.g. chmod rw- file.txt)";
        }
        Node node = current.get(name);
        if (node == null) {
            return "Error: '" + name + "' not found.";
        }
        Permission newPerm = Permission.fromString(perms);
        if (newPerm == null) {
            return "Error: invalid permissions '" + perms + "'. Use format like rwx, r--, rw-, etc.";
        }
        node.setPermission(newPerm);
        return "Permissions for '" + name + "' set to " + newPerm + ".";
    }

    public String help() {
        return "Available commands:\n" +
               "  mkdir <name>          - Create a new directory\n" +
               "  touch <name>          - Create a new empty file\n" +
               "  ls                    - List contents of current directory\n" +
               "  cd <name>             - Change directory (use '..' for parent)\n" +
               "  cat <file>            - Display file contents\n" +
               "  write <file> <text>   - Write text to a file\n" +
               "  rm <name>             - Remove a file or directory\n" +
               "  mv <src> <dest>       - Move/rename a file or directory\n" +
               "  find <name>           - Search for file/directory by name\n" +
               "  chmod <perms> <name>  - Set permissions (e.g. rwx, r--, rw-)\n" +
               "  pwd                   - Print current directory path\n" +
               "  help                  - Show this help message\n" +
               "  exit                  - Exit the file system\n";
    }
}
