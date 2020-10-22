package com.konkuk.controller;

import com.konkuk.UI;
import com.konkuk.asset.Langs;
import com.konkuk.dto.Employee;
import com.konkuk.repository.EmployeeRepository;
import com.konkuk.service.EmployeeService;

import java.util.regex.Pattern;

public class EmployeeController extends Controller {
    EmployeeRepository Erepositry = EmployeeRepository.getInstance();
    public enum Menu {FIND, ADD}
    protected Menu currentMenu;
    EmployeeService service = new EmployeeService(); // 서비스 객체 생성
    public EmployeeController(Menu menu) {
        this.currentMenu = menu;
    }

    public Controller start() {

        if (currentMenu == Menu.FIND) {
            find();
        } else if (currentMenu == Menu.ADD) {
            add();
        }
        // 수정 필요
        return new MainController();
    }

    private void find() {//직원 검색

    }

    private void add() { // 직원 추가
        // Member 생성

        String id,name,salary,yn;


        while(true) {//id 입력
            UI.print2(Langs.EMPLOYEE_ID);
            id = UI.getInput();
            if(service.idcheck(id)) // 체크에서 올바르다면(true) 반복문 빠져가가기
                break;
        }// id 종료
        //이름 시작
        while(true) {

            UI.print2(Langs.EMPLOYEE_NAME);
            name = UI.getInput();
            if(service.namecheck(name)) // 체크에서 올바르다면(true) 반복문 빠져가가기
                break;
        }

        while(true) {

            UI.print2(Langs.EMPLOYEE_SALARY);
            salary = UI.getInput();
            if(service.salarycheck(salary))// 체크에서 올바르다면(true) 반복문 빠져가가기
                break;
        }
        UI.print("*"+Langs.EMPLOYEE_ID+id);
        UI.print("*"+Langs.EMPLOYEE_NAME+name);
        UI.print("*"+Langs.EMPLOYEE_SALARY+salary+"\n");

        while(true) {
            UI.print2("위와 같이 저장하시겠습니까? ");
            yn = UI.getInput();
            if (new EmployeeService().check(yn) == 0) {
                Erepositry.add(new Employee(Integer.parseInt(id), name, Integer.parseInt(salary), 0));
                break;
            }
            else if(new EmployeeService().check(yn) == 1){
                break;
            }
            UI.print(Langs.LETTER_ERROR);
        }




        // member.id ....

        // 모든 service, repository 는 임시로 new 만들어쓰기
//        boolean result = new EmployeeService().add(employee);
        // 성공과 실패의 처리
    }
}
