package com.konkuk.controller;

import com.konkuk.UI;
import com.konkuk.asset.Langs;
import com.konkuk.dto.Employee;
import com.konkuk.repository.EmployeeRepository;
import com.konkuk.service.DayOffInfoService;


import java.io.IOException;

public class DayOffInfoController extends Controller {
    EmployeeRepository Erepositry = EmployeeRepository.getInstance();

    public enum Menu {DayOffInfo}

    protected DayOffInfoController.Menu currentMenu;


    public DayOffInfoController(DayOffInfoController.Menu menu) {
        this.currentMenu = menu;
    }

    public Controller start() {
        if (currentMenu == DayOffInfoController.Menu.DayOffInfo) {
            print(Erepositry.findByExactId(11)); //임시
        }
        // 수정 필요
        return new MainController();
    }


    public Controller print(Employee employee) { //수정해야함
        if (employee.residualDayOff >= 0) {
            UI.print2(Langs.HORIZON);
            UI.print(Langs.DAY_OFF_INFO);
            UI.print2(Langs.HORIZON);
            System.out.println( employee.id +"   "+ employee.name +"   " + employee.residualDayOff);
        } else {
            UI.print2(Langs.HORIZON);
            UI.print(Langs.DAY_OFF_INFO);
            UI.print2(Langs.HORIZON);
            System.out.println( employee.id +"   "+ employee.name +"   " + "0");
        }
        if (employee.residualDayOff == 0) {  //잔여연차=0
            UI.print2(Langs.HORIZON);
            System.out.print("연차를 모두 사용하였습니다. [메인 화면]으로 이동합니다.");

        } else if (employee.residualDayOff >= 1) { //잔여연차 1 이상

            UI.print(Langs.DAY_OFF_INFO_PAGE_REFUND);
            while (true) {

                String menu = UI.getInput();
                if (menu.equals("1")) {
                    new DayOffInfoService().refund_cal(employee.salary, employee.residualDayOff, employee.id);
                    return new MainController();
                } else if (menu.equals("2")) {
                    return new MainController();}
                else {
                    UI.print(Langs.INPUT_ERROR);
                } }}
        else if (employee.residualDayOff < 0) { //잔여연차 0미만

            UI.print(Langs.DAY_OFF_INFO_PAGE);
            while (true) {
                String menu = UI.getInput();
                if (menu.equals("1")) {
                    new DayOffInfoService().cal(employee.salary, employee.residualDayOff, employee.id);
                    return new MainController();
                } else if (menu.equals("2")) {
                    return new MainController();
                } else {
                    UI.print(Langs.INPUT_ERROR);
                }
            }


        }
        return new MainController();
    }
}