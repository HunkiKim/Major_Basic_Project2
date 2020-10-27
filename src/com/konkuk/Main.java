package com.konkuk;

import com.konkuk.controller.Controller;
import com.konkuk.controller.DayOffController;
import com.konkuk.controller.MainController;
import com.konkuk.repository.DayOffRepository;
import com.konkuk.repository.EmployeeRepository;
import com.konkuk.repository.LogRepository;

public class Main {
    public static boolean exit = false;
    public static void main(String[] args) {
        // init
        EmployeeRepository.getInstance();
        DayOffRepository.getInstance();
        LogRepository.getInstance();

        Controller controller = new MainController();

        while(true) {
            if(exit) break;
            controller = controller.start();
        }
    }
}
