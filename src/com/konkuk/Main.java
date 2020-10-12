package com.konkuk;

import com.konkuk.controller.Controller;
import com.konkuk.controller.MainController;

public class Main {
    public static boolean exit = false;
    public static void main(String[] args) {
        Controller controller = new MainController();
        while(true) {
            if(exit) break;
            controller = controller.start();
        }
    }
}
