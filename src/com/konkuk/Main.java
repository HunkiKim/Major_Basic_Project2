package com.konkuk;

import com.konkuk.asset.Langs;
import com.konkuk.controller.Controller;
import com.konkuk.controller.MainController;
import com.konkuk.repository.DayOffRepository;
import com.konkuk.repository.EmployeeRepository;
import com.konkuk.repository.LogRepository;

public class Main {
    public static boolean exit = false;
    public static void main(String[] args) {
        initData();
        Controller controller = new MainController();
        while (!exit) {
            controller = controller.start();
        }
    }

    private static void initData() {
        UI.print2(Langs.HORIZON);
        UI.print(Langs.LOADING_REPOSITORY);
        EmployeeRepository.getInstance();
        DayOffRepository.getInstance();
        LogRepository.getInstance();
        UI.print(Langs.LOADING_REPOSITORY_DONE);
    }
}
