package com.konkuk;

import java.util.Scanner;

public class UI {
    public static void print(String msg) {
        System.out.println(msg);
    }

    public static String getInput(String msg) {
        if(msg != null) print(msg);
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }
}
