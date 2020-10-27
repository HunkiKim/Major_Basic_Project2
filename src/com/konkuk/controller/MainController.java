package com.konkuk.controller;

import com.konkuk.UI;
import com.konkuk.asset.Langs;
import com.konkuk.service.EmployeeService;

public class MainController extends Controller {

    public Controller start() {
        Controller next = null;
        while(true) {
            UI.print2(Langs.MAIN);
            String nextMenu = UI.getInput();
            switch (nextMenu) {
                case "1":
                    next = new EmployeeController(EmployeeController.Menu.FIND);
                    break;
                case "2":
                    next = new EmployeeController(EmployeeController.Menu.ADD);
                    break;
                case "3":
                    next = new LogController(LogController.Menu.LOG);
                    break;
                case "4":
                    System.exit(0);
                    break;
                default:
                    break;
            }
//            UI.print(nextMenu);
            if(nextMenu.equals("")){
                    UI.print(Langs.BLANK_SPACE_ERROR);
                    try {
                        Thread.sleep(2000);
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else if(nextMenu.contains(" ")){
                    UI.print(Langs.BLANK_SPACE_ERROR);
                    try {
                        Thread.sleep(2000);
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else if(new EmployeeService().idcheck(nextMenu)){
                    if(Integer.parseInt(nextMenu)>4 || Integer.parseInt(nextMenu)<1) {
                        UI.print(Langs.NUM_ERROR);
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (next != null){
                    break;
            }
            // 그 외는 공통 메시지 출력 후 다시 받기

        }
        return next;
    }

}
