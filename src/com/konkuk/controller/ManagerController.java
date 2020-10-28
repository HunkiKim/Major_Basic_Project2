package com.konkuk.controller;

import com.konkuk.UI;
import com.konkuk.Utils;
import com.konkuk.asset.Langs;
import com.konkuk.dto.Employee;
import com.konkuk.service.EmployeeService;

import java.io.IOException;

public class ManagerController extends Controller {
    int employeeid;

    public ManagerController(int employee) {
        employeeid = employee;
    }

    @Override
    public Controller start() {
        Controller next = null;


        while(true) {
            UI.print2(Langs.MANAGE_MAIN);
            String nextMenu = UI.getInput();
            switch (nextMenu) {
                case "1":
                    next = new EmployeeManageController(employeeid); //정보 수정 및 삭제 (미완성)
                    break;
                case "2": // 연차 사용 및 수정
                    next = new DayOffController(employeeid);
                    break;
                case "3": // 연차 내역 조회
                    next = new DayOffHistoryController(employeeid);
                    break;
                case "4":// 연차 정보 조회
                      next = new DayOffInfoController(employeeid);
                    break;
                case "B":
                    next = new MainController(); //메인
                case "b":
                    next = new MainController();
                default:
                    break;
            }
            if(nextMenu.equals("")){
                Utils.pause(Langs.BLANK_SPACE_ERROR);
            }
            else if(nextMenu.contains(" ")){
                Utils.pause(Langs.BLANK_SPACE_ERROR);
            }
            else if(new EmployeeService().idcheck(nextMenu)){
                if(Integer.parseInt(nextMenu)>4 || Integer.parseInt(nextMenu)<1) {
                    Utils.pause(Langs.NUM_ERROR);
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
