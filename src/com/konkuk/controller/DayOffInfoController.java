package com.konkuk.controller;

import com.konkuk.UI;
import com.konkuk.asset.Langs;
import com.konkuk.dto.Employee;
import com.konkuk.repository.EmployeeRepository;
import com.konkuk.service.DayOffInfoService;

import java.io.IOException;

public class DayOffInfoController extends Controller {

    public enum Menu {DayOffInfo}

    protected DayOffInfoController.Menu currentMenu;


    public DayOffInfoController(DayOffInfoController.Menu menu) {
        this.currentMenu = menu;
    }

    public Controller start() {
        if (currentMenu == DayOffInfoController.Menu.DayOffInfo) {
            print(10, "임시", -1); //임시
        }
        // 수정 필요
        return new MainController();
    }

    public void print(int id, String name, int residualDayOff) { //수정해야함
        System.out.println("사번: " + id + " 이름:" + name + " 잔여 연차:" + residualDayOff);
        if (residualDayOff == 0) {
            System.out.println("-------------------------------------------------------------");
            System.out.print("연차를 모두 사용하였습니다. 메인 화면으로 돌아갑니다.");

        } else if (residualDayOff >= 1) {
            System.out.println("-----------------------------------------------------------");
            UI.print(Langs.DAY_OFF_INFO_PAGE_REFUND);
            while (true) {
            String menu = UI.getInput();
            if (menu.equals("1")) {
                new DayOffInfoService().cal(0, 0);
            } else if (menu.equals("2")) {
                  break;
            }}
        } else if (residualDayOff < 0) {
            System.out.println("-------------------------------------------------------------");
            UI.print(Langs.DAY_OFF_INFO_PAGE);
        }
        while (true) {
            String menu = UI.getInput();
            if (menu.equals("1")) {
                new DayOffInfoService().cal(0, 0);
            } else if (menu.equals("2")) {
                  break;
            }
        }
    }

}



