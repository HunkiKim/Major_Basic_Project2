package com.konkuk.controller;

import com.konkuk.UI;
import com.konkuk.asset.Langs;

public class MainController extends Controller {

    public Controller start() {
        Controller next = null;
        while(true) {
            UI.print(Langs.MAIN);
            String nextMenu = UI.getInput();
            switch (nextMenu) {
                case "1":
                    next = new EmployeeController(EmployeeController.Menu.ADD);
                case "2":
                    next = new EmployeeController(EmployeeController.Menu.FIND);
                default:
                    break;
            }
            if (next != null) break;
            // 그 외는 공통 메시지 출력 후 다시 받기
        }
        return next;
    }

}
