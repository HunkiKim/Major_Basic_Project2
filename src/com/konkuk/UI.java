package com.konkuk;

import com.konkuk.asset.Settings;

import java.util.Scanner;

public class UI {
    public static void print(String msg) {
        System.out.println(msg);
    }
    public static void print2(String msg){ System.out.print(msg);}
    public static String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }

    public static int getInput1(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
