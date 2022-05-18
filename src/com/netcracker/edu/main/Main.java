package com.netcracker.edu.main;


public class Main {

    public static void main(String[] args) {
        print(args);
        sort(args);
    }

    public static void print(String[] args) {
        for(String arg : args)
            System.out.println(arg);
    }

    public static void sort(String[] args) {
        for(int i = 0; i < args.length; i++) {
            for(int j = 0; j < args.length-i-1;j++) {
                if(args[j].compareTo(args[j+1])>0) {
                    String temp = args[j];
                    args[j] = args[j+1];
                    args[j+1] = temp;
                }
            }
        }
    }
}