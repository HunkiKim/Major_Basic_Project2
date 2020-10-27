package com.konkuk.controller;

import com.konkuk.UI;
import com.konkuk.asset.Langs;
import com.konkuk.dto.Employee;
import com.konkuk.repository.EmployeeRepository;
import com.konkuk.repository.LogRepository;
import com.konkuk.service.EmployeeService;

import java.io.IOException;


public class EmployeeManageController extends Controller {
    int employeeId;
    LogRepository L = LogRepository.getInstance();
    EmployeeRepository Erepository = EmployeeRepository.getInstance();
    public EmployeeManageController(int employeeId) {
        this.employeeId = employeeId;


    }

    public Controller start() {
        UI.print2(Langs.EMPLOYEE_MANAGE_MAIN);


        while(true) {
            UI.print2("입력: ");
            String menu = UI.getInput();

            if(menu.equals("B") || menu.equals("b")){
                return new MainController();
            }
            if(menu.equals("2")) { // 삭제 이용
                while(true) {
                    String yn;
                    UI.print2("선택한 대상을 삭제하시겠습니까? ");
                    yn = UI.getInput();
                    if(yn.equals("b") || yn.equals("B")){
                        return new MainController();
                    }
                    if (new EmployeeService().check(yn) == 0) {
                        try{
                            L.addLog("[사원삭제] ", "사원번호 : " + Erepository.findByExactId(employeeId).getId()+ " 사원이름 : "+ Erepository.findByExactId(employeeId).getName()+
                                    " 연봉 : "+ Erepository.findByExactId(employeeId).getSalary()+ " 잔여연차 : "+Erepository.findByExactId(employeeId).getResidualDayOff());
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
                    int check = 0;
                    UI.print2(Langs.EMPLOYEE_ID);
                    id = UI.getInput();
                    if (id.equals("B") || id.equals("b")) {
                        return new MainController();
                    }
                    if (new EmployeeService().idcheck(id)) // 체크에서 올바르다면(true) 반복문 빠져가가기
                        for (int i = 0; i < Erepository.findById(Integer.parseInt(id)).size(); i++) {
                            if (Erepository.findById(Integer.parseInt(id)).get(i).id == Integer.parseInt(id)) {
                                check = 1;
                            }
                        }
                    if (check == 1) {
                        UI.print("이미 같은 사번이 존재합니다. 다시 입력해주세요");
                        continue;
                    }// id 종료
                    break;
                }
                //이름 시작
                while(true) {

                    UI.print2(Langs.EMPLOYEE_NAME);
                    name = UI.getInput();
                    if(name.equals("B") || name.equals("b")){
                        return new MainController();
                    }
                    if(new EmployeeService().namecheck(name)) // 체크에서 올바르다면(true) 반복문 빠져가가기
                        break;
                }

                while(true) {

                    UI.print2(Langs.EMPLOYEE_SALARY);
                    salary = UI.getInput();
                    if(salary.equals("B") || salary.equals("b")){
                        return new MainController();
                    }
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
                            L.addLog("[사원수정] ","사원번호 : " + Erepository.findByExactId(employeeId).getId()+ " 사원이름 : "+ Erepository.findByExactId(employeeId).getName()+
                                    " 연봉 : "+ Erepository.findByExactId(employeeId).getSalary()+ " 잔여연차 : "+Erepository.findByExactId(employeeId).getResidualDayOff() +
                                    " -> " + "사원번호 : "+ id + " 사원이름 : " + name + "연봉 : " + salary + " 잔여연차 : "+ Erepository.findByExactId(employeeId).getResidualDayOff());
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
