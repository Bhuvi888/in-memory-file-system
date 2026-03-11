package com.filesystem.model;

public class Permission {
    private boolean read;
    private boolean write;
    private boolean execute;

    public Permission(boolean read, boolean write, boolean execute) {
        this.read = read;
        this.write = write;
        this.execute = execute;
    }

    public static Permission defaultFilePermission() {
        return new Permission(true, true, false);
    }

    public static Permission defaultDirectoryPermission() {
        return new Permission(true, true, true);
    }

    public static Permission fromString(String perms) {
        if (perms == null || perms.length() != 3) {
            return null;
        }
        boolean r = perms.charAt(0) == 'r';
        boolean w = perms.charAt(1) == 'w';
        boolean x = perms.charAt(2) == 'x';

        // Validate characters
        if ((perms.charAt(0) != 'r' && perms.charAt(0) != '-') ||
            (perms.charAt(1) != 'w' && perms.charAt(1) != '-') ||
            (perms.charAt(2) != 'x' && perms.charAt(2) != '-')) {
            return null;
        }

        return new Permission(r, w, x);
    }

    public boolean canRead() {
        return read;
    }

    public boolean canWrite() {
        return write;
    }

    public boolean canExecute() {
        return execute;
    }

    @Override
    public String toString() {
        return (read ? "r" : "-") + (write ? "w" : "-") + (execute ? "x" : "-");
    }
}
