package com.filesystem;

import com.filesystem.service.FileSystemService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FileSystemService fs = new FileSystemService();

        System.out.println("In-Memory File System");
        System.out.println("Type 'help' for a list of commands.\n");

        while (true) {
            System.out.print(fs.pwd() + "> ");
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            String[] cmd = input.split(" ", 3);
            String result = null;

            switch (cmd[0]) {
                case "mkdir":
                    result = cmd.length < 2 ? "Error: usage: mkdir <name>" : fs.mkdir(cmd[1]);
                    break;
                case "touch":
                    result = cmd.length < 2 ? "Error: usage: touch <name>" : fs.touch(cmd[1]);
                    break;
                case "ls":
                    result = fs.ls();
                    break;
                case "cd":
                    result = cmd.length < 2 ? "Error: usage: cd <name>" : fs.cd(cmd[1]);
                    break;
                case "cat":
                    result = cmd.length < 2 ? "Error: usage: cat <file>" : fs.cat(cmd[1]);
                    break;
                case "write":
                    if (cmd.length < 3) {
                        result = "Error: usage: write <file> <text>";
                    } else {
                        result = fs.write(cmd[1], cmd[2]);
                    }
                    break;
                case "rm":
                    result = cmd.length < 2 ? "Error: usage: rm <name>" : fs.rm(cmd[1]);
                    break;
                case "mv":
                    if (cmd.length < 3) {
                        result = "Error: usage: mv <source> <destination>";
                    } else {
                        // Re-split to get exactly two args for mv
                        String[] mvParts = input.split(" ");
                        if (mvParts.length < 3) {
                            result = "Error: usage: mv <source> <destination>";
                        } else {
                            result = fs.mv(mvParts[1], mvParts[2]);
                        }
                    }
                    break;
                case "find":
                    result = cmd.length < 2 ? "Error: usage: find <name>" : fs.find(cmd[1]);
                    break;
                case "chmod":
                    if (cmd.length < 3) {
                        result = "Error: usage: chmod <permissions> <name>";
                    } else {
                        String[] chmodParts = input.split(" ");
                        if (chmodParts.length < 3) {
                            result = "Error: usage: chmod <permissions> <name>";
                        } else {
                            result = fs.chmod(chmodParts[1], chmodParts[2]);
                        }
                    }
                    break;
                case "pwd":
                    result = fs.pwd();
                    break;
                case "help":
                    result = fs.help();
                    break;
                case "exit":
                    System.out.println("Goodbye!");
                    sc.close();
                    return;
                default:
                    result = "Unknown command: '" + cmd[0] + "'. Type 'help' for available commands.";
            }

            if (result != null && !result.isEmpty()) {
                System.out.println(result);
            }
        }
    }
}