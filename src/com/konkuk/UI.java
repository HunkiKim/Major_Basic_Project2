package com.konkuk;

import java.util.Scanner;

public class UI {
    public static void print(String msg) {
        System.out.println(msg);
    }

    public static String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }
}
