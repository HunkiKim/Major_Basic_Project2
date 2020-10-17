package com.konkuk.controller;

import com.konkuk.UI;
import com.konkuk.dto.Employee;
import com.konkuk.service.EmployeeService;

public class EmployeeController extends Controller {
    public enum Menu {FIND, ADD}
    protected Menu currentMenu;

    public EmployeeController(Menu menu) {
        this.currentMenu = menu;
    }

    public Controller start() {
        UI.print("화이팅.");
        if (currentMenu == Menu.FIND) {
            find();
        } else if (currentMenu == Menu.ADD) {
            add();
        }
        // 수정 필요
        return new MainController();
    }

    private void find() {
    }

    private void add() {
        // Member 생성
//        Employee employee = new Employee();
        // member.id ....

        // 모든 service, repository 는 임시로 new 만들어쓰기
//        boolean result = new EmployeeService().add(employee);
        // 성공과 실패의 처리
    }
}
