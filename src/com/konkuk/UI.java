package com.konkuk;

import com.konkuk.asset.Settings;

import java.util.Scanner;

public class UI {
    public static void print(String msg) {
        System.out.println(msg);
    }

    public static String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }

    public static void debug(String msg) {
        if(Settings.DEBUG) print("Debug: " + msg);
    }
}
