package com.konkuk.controller;

import com.konkuk.UI;
import com.konkuk.asset.Langs;
import com.konkuk.dto.Employee;
import com.konkuk.repository.EmployeeRepository;
import com.konkuk.service.DayOffInfoService;


import java.io.IOException;

public class DayOffInfoController extends Controller {
    int employeeId;


    public enum Menu {DayOffInfo}

    protected DayOffInfoController.Menu currentMenu;
    public DayOffInfoController(int employeeId) {
        this.employeeId = employeeId;

    }
    EmployeeRepository employeeRepository = EmployeeRepository.getInstance();
    Employee employee = employeeRepository.findByExactId(employeeId);

    public Controller start() {
            print(employeeId);
        return new MainController();
    }


    public Controller print(int employeeId) {
        if (employeeRepository.findByExactId(employeeId).getResidualDayOff()>= 1) { //잔여연차 1이상
            UI.print2(Langs.HORIZON);
            UI.print(Langs.DAY_OFF_INFO);
            UI.print2(Langs.HORIZON);
           UI.print( employeeRepository.findByExactId(employeeId).getId()+"   "+ employeeRepository.findByExactId(employeeId).getName() +"   "
                    + employeeRepository.findByExactId(employeeId).getResidualDayOff());
        } else {
            UI.print2(Langs.HORIZON);
            UI.print(Langs.DAY_OFF_INFO);
            UI.print2(Langs.HORIZON);
            UI.print( employeeRepository.findByExactId(employeeId).getId()+"   "+ employeeRepository.findByExactId(employeeId).getName() +"   "
                    + 0);
        }
        if (employeeRepository.findByExactId(employeeId).getResidualDayOff() == 0) {  //잔여연차=0
            UI.print2(Langs.HORIZON);
            UI.print("연차를 모두 사용하였습니다. [메인 화면]으로 이동합니다.");

        } else if (employeeRepository.findByExactId(employeeId).getResidualDayOff() >= 1) { //잔여연차 1 이상

            UI.print(Langs.DAY_OFF_INFO_PAGE_REFUND);
            while (true) {

                String menu = UI.getInput();
                if (menu.equals("1")) {
                    new DayOffInfoService().refund_cal(employeeRepository.findByExactId(employeeId).getSalary(),employeeRepository.findByExactId(employeeId).getResidualDayOff()
                            ,employeeRepository.findByExactId(employeeId).getName());
                    return new MainController();
                } else if (menu.equals("2")) {
                    return new MainController();}
                else {
                    UI.print(Langs.INPUT_ERROR);
                } }}
        else if (employeeRepository.findByExactId(employeeId).getResidualDayOff() < 0) { //잔여연차 0미만

            UI.print(Langs.DAY_OFF_INFO_PAGE);
            while (true) {
                String menu = UI.getInput();
                if (menu.equals("1")) {
                    new DayOffInfoService().cal(employeeRepository.findByExactId(employeeId).getSalary(),employeeRepository.findByExactId(employeeId).getResidualDayOff()
                            ,employeeRepository.findByExactId(employeeId).getName());
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