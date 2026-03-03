package com.filesystem;

import com.filesystem.service.FileSystemService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        FileSystemService fs = new FileSystemService();

        while(true){

            System.out.print(fs.pwd()+">");

            String input = sc.nextLine();
            String[] cmd = input.split(" ");

            switch(cmd[0]){
                case "mkdir":
                    fs.mkdir(cmd[1]);
                    break;
                case "touch":
                    fs.touch(cmd[1]);
                    break;
                case "ls":
                    fs.ls();
                    break;
                case "cd":
                    fs.cd(cmd[1]);
                    break;
                default:
                    System.out.println("NO SUCH COMMAND FOUND");
            }
        }
    }
}