package com.konkuk.controller;

import com.konkuk.UI;
import com.konkuk.Utils;
import com.konkuk.Utils.InputType;
import com.konkuk.asset.Langs;
import com.konkuk.dto.Employee;
import com.konkuk.exception.IllegalLengthException;
import com.konkuk.exception.IllegalLetterException;
import com.konkuk.repository.EmployeeRepository;
import com.konkuk.repository.LogRepository;
import com.konkuk.service.EmployeeService;
import com.sun.security.auth.callback.TextCallbackHandler;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.regex.Pattern;

public class EmployeeController extends Controller {
    LogRepository L = LogRepository.getInstance();
    EmployeeRepository Erepositry = EmployeeRepository.getInstance();
    EmployeeService employeeService = new EmployeeService();

    public enum Menu {FIND, ADD}
    protected Menu currentMenu;

    public EmployeeController(Menu menu) {
        this.currentMenu = menu;
    }

    public Controller start() {

        if (currentMenu == Menu.FIND) {
            return find();
        } else if (currentMenu == Menu.ADD) {
            add();
        }
        // 수정 필요
        return new MainController();
    }

    public Controller find() {//직원 검색
        List<Employee> exactfind = null;
        String input;
        while(true) {
            UI.print2(Langs.EMPLOYEE_TARGET);
            input = UI.getInput();
            if(input.equals("B") || input.equals("b")){
                return new MainController();
            }
            //공통
            if(input.equals("")){
                UI.print(Langs.BLANK_SPACE_ERROR);
                try {
                    Thread.sleep(2000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            else if(input.contains(" ")){
                UI.print(Langs.BLANK_SPACE_ERROR);
                try {
                    Thread.sleep(2000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            if(input.charAt(0)=='0' && input.length()!=1){
                UI.print(Langs.BLANK_SPACE_ERROR);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            //공통
            try {
                exactfind = employeeService.getEmployees(input);
            }catch(StringIndexOutOfBoundsException e){
                UI.print(Langs.EMPTY_ERROR);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                continue;
            }
            catch (IllegalLetterException e) {
                UI.print(Langs.LETTER_ERROR);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                continue;
            } catch (IllegalLengthException e) {
                UI.print(Langs.LENGTH_ERROR);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                continue;
            }

            if(exactfind == null || exactfind.size() == 0) {
                UI.print(Langs.FIND_ERROR);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                exactfind.forEach(employee -> {
                    UI.print("*" + Langs.EMPLOYEE_ID + employee.id);
                    UI.print("*" + Langs.EMPLOYEE_NAME + employee.name);
                    UI.print("*" + Langs.EMPLOYEE_SALARY + (int) employee.salary + "\n");
                });
                break;
            }
        }
            //정상적으로 나왔다면 List들 출력후 y,n 할건지 출력!  y라면 관리자메뉴 n이라면 다시 메인메뉴!
            //repository완성후 마무리

            while(true){ // 찾은 애들중 선택
                String id=""; //사번
                int check=0; // exactfind가 있는지 체크
                UI.print2("검색 대상 선택: ");
                id = UI.getInput();
                if(id.equals("B") || id.equals("b")){
                    return new MainController();
                }
                //공통
                if(id.equals("")){
                    UI.print(Langs.BLANK_SPACE_ERROR);
                    try {
                        Thread.sleep(2000);
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                else if(id.contains(" ")){
                    UI.print(Langs.BLANK_SPACE_ERROR);
                    try {
                        Thread.sleep(2000);
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                //공통
                if(!employeeService.idcheck(id)){// 숫자가 아니면 다시
                    continue;
                }

                for(int i=0; i<exactfind.size(); i++){
                    if(Integer.parseInt(id)==exactfind.get(i).id){
                        check=1;
                    }
                }

                if(check==0){
                    UI.print(Langs.FIND_ERROR);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                while(true) {
                    if (Integer.parseInt(id) == Erepositry.findByExactId(Integer.parseInt(id)).id) {
                        UI.print("*" + Langs.EMPLOYEE_ID + Erepositry.findByExactId(Integer.parseInt(id)).id);
                        UI.print("*" + Langs.EMPLOYEE_NAME + Erepositry.findByExactId(Integer.parseInt(id)).name);
                        UI.print("*" + Langs.EMPLOYEE_SALARY + (int) Erepositry.findByExactId(Integer.parseInt(id)).salary + "\n");
                    } else { //검색대상 오류
                        // 만약 올바르지 않은 숫자거나 문자라면
                        UI.print(Langs.LETTER_ERROR);
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }   // 올바른 숫자일경우

                    UI.print2("검색한 대상을 선택하시겠습니까? ");
                    String yn = UI.getInput();
                    if (yn.equals("B") || yn.equals("b")) {
                        return new MainController();
                    }
                    //공통
                    if(yn.equals("")){
                        UI.print(Langs.BLANK_SPACE_ERROR);
                        try {
                            Thread.sleep(2000);
                        } catch(InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                    else if(yn.contains(" ")){
                        UI.print(Langs.BLANK_SPACE_ERROR);
                        try {
                            Thread.sleep(2000);
                        } catch(InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                    //공통
                    if (employeeService.check(yn) == 0) {

                        return new ManagerController(Integer.parseInt(id));
                    } else if (employeeService.check(yn) == 1) {
                        return new MainController();
                    }
                    UI.print(Langs.LETTER_ERROR); // 문자 잘못입력시
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }





    private void add() { // 직원 추가
        // Member 생성

        String id,name,salary,yn;

        ArrayList findid = new ArrayList();

        while(true) {//id 입력
            int check=0; //find 체크
            UI.print2(Langs.EMPLOYEE_ID);
            id = UI.getInput();
            if(id.equals("B") || id.equals("b")){
                return;
            }// 체크에서 올바르다면(true) 반복문 빠져가가기
            if(id.equals("")){
                UI.print(Langs.BLANK_SPACE_ERROR);
                try {
                    Thread.sleep(2000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            else if(id.contains(" ")){
                UI.print(Langs.BLANK_SPACE_ERROR);
                try {
                    Thread.sleep(2000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            //공통 항목
            if(employeeService.idcheck(id)) {
                for(int i=0; i<Erepositry.findById(Integer.parseInt(id)).size(); i++){
                    if(Erepositry.findById(Integer.parseInt(id)).get(i).id==Integer.parseInt(id)){
                        check=1;
                    }
                }
                if(check==1){
                    UI.print("이미 같은 사번이 존재합니다. 다시 입력해주세요");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                break;
            }

        }// id 종료
        //이름 시작
        while(true) {

            UI.print2(Langs.EMPLOYEE_NAME);
            name = UI.getInput();
            if(name.equals("B") || name.equals("b")){
                return;
            }
            //공통
            if(name.equals("")){
                UI.print(Langs.BLANK_SPACE_ERROR);
                try {
                    Thread.sleep(2000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            else if(name.contains(" ")){
                UI.print(Langs.BLANK_SPACE_ERROR);
                try {
                    Thread.sleep(2000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            if(employeeService.namecheck(name)) // 체크에서 올바르다면(true) 반복문 빠져가가기
                break;
        }

        while(true) {

            UI.print2(Langs.EMPLOYEE_SALARY);
            salary = UI.getInput();
            if(salary.equals("B") || salary.equals("b")){
                return;
            }
            //공통
            if(salary.equals("")){
                UI.print(Langs.BLANK_SPACE_ERROR);
                try {
                    Thread.sleep(2000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            else if(salary.contains(" ")){
                UI.print(Langs.BLANK_SPACE_ERROR);
                try {
                    Thread.sleep(2000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            //공통
            if(employeeService.salarycheck(salary))// 체크에서 올바르다면(true) 반복문 빠져가가기
                break;
        }
        UI.print("*"+Langs.EMPLOYEE_ID+id);
        UI.print("*"+Langs.EMPLOYEE_NAME+name);
        UI.print("*"+Langs.EMPLOYEE_SALARY+salary+"\n");

        while(true) {
            UI.print2("위와 같이 저장하시겠습니까? ");
            yn = UI.getInput();
            if(yn.equals("B") || yn.equals("b")){
                return;
            }
            if(yn.equals("")){
                UI.print(Langs.BLANK_SPACE_ERROR);
                try {
                    Thread.sleep(2000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            else if(yn.contains(" ")){
                UI.print(Langs.BLANK_SPACE_ERROR);
                try {
                    Thread.sleep(2000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            if (employeeService.check(yn) == 0) {
                try{
                    L.addLog("[사원추가] " , "사원번호 : "+id +" 사원이름 : "+name+ "연봉 : "+ salary + " 잔여연차 : "+"0");
                    Erepositry.add(new Employee(Integer.parseInt(id), name, Integer.parseInt(salary), 0));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            else if(employeeService.check(yn) == 1){
                break;
            }
            UI.print(Langs.LETTER_ERROR);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }




        // member.id ....

        // 모든 service, repository 는 임시로 new 만들어쓰기
//        boolean result = employeeService.add(employee);
        // 성공과 실패의 처리
    }
}
