package com.konkuk.controller;

import com.konkuk.UI;
import com.konkuk.asset.Langs;
import com.konkuk.dto.Employee;
import com.konkuk.repository.EmployeeRepository;
import com.konkuk.service.EmployeeService;
import java.util.List;
import java.io.IOException;
import java.util.regex.Pattern;

public class EmployeeController extends Controller {
    EmployeeRepository Erepositry = EmployeeRepository.getInstance();
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

        String target;
        while(true) {
            UI.print2(Langs.EMPLOYEE_TARGET);
            target = UI.getInput();

            if (new EmployeeService().lettercheck(target)) { // 숫
                if (new EmployeeService().numletter(target) > -1) { //숫자 길이 확인
                    if(Erepositry.findById(Integer.parseInt(target)).size()==0 && Erepositry.findBySalary(Integer.parseInt(target)).size()==0){
                        UI.print(Langs.FIND_ERROR);
                        continue;
                    }
                    if (new EmployeeService().numletter(target) == 1) { //사번
                        for (int i = 0; i < Erepositry.findById(Integer.parseInt(target)).size(); i++) {  //찾은거 복사
                            UI.print("*" + Langs.EMPLOYEE_ID + Erepositry.findById(Integer.parseInt(target)).get(i).id);
                            UI.print("*" + Langs.EMPLOYEE_NAME + Erepositry.findById(Integer.parseInt(target)).get(i).name);
                            UI.print("*" + Langs.EMPLOYEE_SALARY + Erepositry.findById(Integer.parseInt(target)).get(i).salary + "\n");
                        }
                    } else if (new EmployeeService().numletter(target) == 2) { // 연봉
                        for (int i = 0; i < Erepositry.findBySalary(Integer.parseInt(target)).size(); i++) {  //찾은거 복사
                            UI.print("*" + Langs.EMPLOYEE_ID + Erepositry.findBySalary(Integer.parseInt(target)).get(i).id);
                            UI.print("*" + Langs.EMPLOYEE_NAME + Erepositry.findBySalary(Integer.parseInt(target)).get(i).name);
                            UI.print("*" + Langs.EMPLOYEE_SALARY + Erepositry.findBySalary(Integer.parseInt(target)).get(i).salary + "\n");
                        }
                    }
                    break; // 숫자 종료
                } else if (new EmployeeService().numletter(target) == -1 || new EmployeeService().numletter(target) == -3) { // 길이가 안맞는 경우
                    UI.print(Langs.LENGTH_ERROR); //길이 오류
                } else { // 문자의 경우
                    if(Erepositry.findByName(target).size()==0){
                        UI.print(Langs.FIND_ERROR);
                        continue;
                    }
                    for (int i = 0; i < Erepositry.findByName(target).size(); i++) {  //찾은거 복사
                        UI.print("*" + Langs.EMPLOYEE_ID + Erepositry.findByName(target).get(i).id);
                        UI.print("*" + Langs.EMPLOYEE_NAME + Erepositry.findByName(target).get(i).name);
                        UI.print("*" + Langs.EMPLOYEE_SALARY + Erepositry.findByName(target).get(i).salary + "\n");
                    }
                    break;
                }

            } else { // 문자가 섞여있는경우
                if (new EmployeeService().salarmeasure(target)) { // 1000만 이런형식인지 확인ㅇ
                   if(Erepositry.findBySalary(new EmployeeService().salaryconversion(target)).size()==0){
                       UI.print(Langs.FIND_ERROR);
                       continue;
                   }
                    for (int i = 0; i < Erepositry.findBySalary(new EmployeeService().salaryconversion(target)).size(); i++) {  //찾은거 복사
                        UI.print("*" + Langs.EMPLOYEE_ID + Erepositry.findBySalary(new EmployeeService().salaryconversion(target)).get(i).id);
                        UI.print("*" + Langs.EMPLOYEE_NAME + Erepositry.findBySalary(new EmployeeService().salaryconversion(target)).get(i).name);
                        UI.print("*" + Langs.EMPLOYEE_SALARY + Erepositry.findBySalary(new EmployeeService().salaryconversion(target)).get(i).salary + "\n");
                    }
                    break; // 샐러리 만 형식 종료
                }



                UI.print(Langs.LETTER_ERROR); //문자섞임오류
            }
        }
            //정상적으로 나왔다면 List들 출력후 y,n 할건지 출력!  y라면 관리자메뉴 n이라면 다시 메인메뉴!
            //repository완성후 마무리

            while(true){ // 찾은 애들중 선택
                String id=""; //사번
                UI.print2("검색 대상 선택: ");
                id = UI.getInput();
                if(Integer.parseInt(id) == Erepositry.findByExactId(Integer.parseInt(id)).id){
                    UI.print("*"+Langs.EMPLOYEE_ID+Erepositry.findByExactId(Integer.parseInt(id)).id);
                    UI.print("*"+Langs.EMPLOYEE_NAME+Erepositry.findByExactId(Integer.parseInt(id)).name);
                    UI.print("*"+Langs.EMPLOYEE_SALARY+Erepositry.findByExactId(Integer.parseInt(id)).salary+"\n");
                }else { //검색대상 오류
                    // 만약 올바르지 않은 숫자거나 문자라면
                    UI.print(Langs.LETTER_ERROR);
                    continue;
                }   // 올바른 숫자일경우

                UI.print2("위와 같이 저장하시겠습니까? ");
                String yn = UI.getInput();
                if (new EmployeeService().check(yn) == 0) {
                    return new ManagerController(Integer.parseInt(id));
                }
                else if(new EmployeeService().check(yn) == 1){
                    return new MainController();
                }
                UI.print(Langs.LETTER_ERROR); // 문자 잘못입력시
            }

        }





    private void add() { // 직원 추가
        // Member 생성

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
            UI.print2("위와 같이 저장하시겠습니까? ");
            yn = UI.getInput();
            if (new EmployeeService().check(yn) == 0) {
                try{
                    Erepositry.add(new Employee(Integer.parseInt(id), name, Integer.parseInt(salary), 0));
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




        // member.id ....

        // 모든 service, repository 는 임시로 new 만들어쓰기
//        boolean result = new EmployeeService().add(employee);
        // 성공과 실패의 처리
    }
}
