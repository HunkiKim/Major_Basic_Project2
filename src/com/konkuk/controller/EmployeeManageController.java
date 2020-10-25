package com.konkuk.controller;

import com.konkuk.UI;
import com.konkuk.asset.Langs;
import com.konkuk.dto.Employee;
import com.konkuk.repository.EmployeeRepository;
import com.konkuk.service.EmployeeService;

import java.io.IOException;


public class EmployeeManageController extends Controller {
    int employeeId;
    EmployeeRepository Erepository = EmployeeRepository.getInstance();
    public EmployeeManageController(int employeeId) {
        this.employeeId = employeeId;


    }

    public Controller start() {
        UI.print(Langs.EMPLOYEE_MANAGE_MAIN);
        UI.print2("입력: ");
        String menu = UI.getInput();
        while(true) {
            UI.print2(Langs.HORIZON);

            if(menu.equals("2")) { // 삭제 이용
                while(true) {
                    String yn;
                    UI.print2("선택한 대상을 삭제하시겠습니까? ");
                    yn = UI.getInput();
                    if (new EmployeeService().check(yn) == 0) {
                        try{
                            Erepository.delete(employeeId);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    else if(new EmployeeService().check(yn) == 1){
                        break;
                    }
                    UI.print(Langs.LETTER_ERROR);
                }

                break;
            } else if(menu.equals("1")) { //add 와 동일
                String id,name,salary,yn;
                while(true) {//id 입력
                    UI.print2(Langs.EMPLOYEE_ID);
                    id = UI.getInput();
                    if(new EmployeeService().idcheck(id)) // 체크에서 올바르다면(true) 반복문 빠져가가기
                        break;
                }// id 종료
                //이름 시작
                while(true) {

                    UI.print2(Langs.EMPLOYEE_NAME);
                    name = UI.getInput();
                    if(new EmployeeService().namecheck(name)) // 체크에서 올바르다면(true) 반복문 빠져가가기
                        break;
                }

                while(true) {

                    UI.print2(Langs.EMPLOYEE_SALARY);
                    salary = UI.getInput();
                    if(new EmployeeService().salarycheck(salary))// 체크에서 올바르다면(true) 반복문 빠져가가기
                        break;
                }
                UI.print("*"+Langs.EMPLOYEE_ID+id);
                UI.print("*"+Langs.EMPLOYEE_NAME+name);
                UI.print("*"+Langs.EMPLOYEE_SALARY+salary+"\n");

                while(true) {
                    UI.print2("검색한 대상을 선택하시겠습니까? ");
                    yn = UI.getInput();
                    if (new EmployeeService().check(yn) == 0) {
                        try{
                            Erepository.update(employeeId, new Employee(Integer.parseInt(id), name, Integer.parseInt(salary), 0));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    else if(new EmployeeService().check(yn) == 1){
                        break;
                    }
                    UI.print(Langs.LETTER_ERROR);
                }
                break;
            }
            else if(menu.equals("B")|| menu.equals("b")){
                break;
                }
            else {
                UI.print(Langs.INPUT_ERROR);
            }
        }
        return new MainController();
    }

}
