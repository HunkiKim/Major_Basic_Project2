package com.konkuk.service;

import com.konkuk.controller.Controller;
import com.konkuk.controller.MainController;
import com.konkuk.repository.EmployeeRepository;

public class Main {
    public static boolean exit = false;
    public static void main(String[] args) {
        // init
        EmployeeRepository.getInstance();

        Controller controller = new MainController();
        while(true) {
            if(exit) break;
            controller = controller.start();
        }
    }
}
