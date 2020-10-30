package com.konkuk.controller;

import com.konkuk.UI;
import com.konkuk.Utils;
import com.konkuk.asset.Langs;
import com.konkuk.dto.Employee;
import com.konkuk.repository.EmployeeRepository;
import com.konkuk.repository.LogRepository;
import com.konkuk.service.DayOffInfoService;
import com.konkuk.service.EmployeeService;


public class DayOffInfoController extends Controller {
    LogRepository log = LogRepository.getInstance();
    DayOffInfoService dayOffInfoService= new DayOffInfoService();
    EmployeeService employeeService= new EmployeeService();
    int employeeId;
    public DayOffInfoController(int employeeId) {
        this.employeeId = employeeId;
    }

    public Controller start() {
        print(employeeId);
        return new MainController();
    }

    public Controller print(int employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        String salary = employee.salary.toString();

        if (employee.getResidualDayOff() == 0) {  //잔여연차=0
            UI.print2(Langs.HORIZON);
            UI.print(Langs.DAY_OFF_INFO);
            UI.print2(Langs.HORIZON);
            UI.print(employee.getId()+"   "+ employee.getName() +"   "+ 0);
            UI.print("\n연차를 모두 사용하였습니다. [메인 화면]으로 이동합니다.");
            log.addLog("[연차정보 조회] " ,
                    "사원번호 :"+employee.getId()+
                            " 사원이름 :"+employee.getName()+
                            " 연봉 :" + salary+
                            " 잔여연차 :"+employee.getResidualDayOff());
        }
        else if (employee.getResidualDayOff() >= 1) { //잔여연차 1 이상
            UI.print2(Langs.HORIZON);
            UI.print(Langs.DAY_OFF_INFO);
            UI.print2(Langs.HORIZON);
            UI.print(employee.getId()+"   "+ employee.getName() +"   "
                    + employee.getResidualDayOff());

            log.addLog("[연차정보 조회] " ,
                    "사원번호 :"+employee.getId()+
                            " 사원이름 :"+employee.getName()+
                            " 연봉 :" + salary+
                            " 잔여연차 :"+ employee.getResidualDayOff());
            UI.print2(Langs.DAY_OFF_INFO_PAGE_REFUND);
            while (true) { //1.환급액 조회 2.메인메뉴 이동
                String menu = UI.getInput();
                if (menu.equals("1")) {
                    UI.print(employee.getName()+"님의 연차 미사용으로 인한 환급액은 "
                            +Utils.floatToString(
                                    dayOffInfoService.refund_cal(
                                            employee.getSalary().floatValue(),
                                            employee.getResidualDayOff()))+"원 입니다");
                    log.addLog("[환급액 조회] " , "사원번호 :"+employee.getId()+
                            " 사원이름 :"+employee.getName()+
                            " 연봉 :" + salary+
                            " 잔여연차 :"+employee.getResidualDayOff()+
                            " 환급액 :"+ Utils.floatToString(
                                    dayOffInfoService.refund_cal(
                                            employee.getSalary().floatValue(),
                                            employee.getResidualDayOff()
                                    )));
                    return new MainController();
                } else if (menu.equals("2")) {
                    return new MainController();}
                else {
                    Utils.pause("올바르지 않은 입력입니다. 다시 입력해주세요");
                    UI.print2("입력:");
                } }}

        else if (employee.getResidualDayOff() < 0) { //잔여연차 0미만
            UI.print2(Langs.HORIZON);
            UI.print(Langs.DAY_OFF_INFO);
            UI.print2(Langs.HORIZON);
            UI.print( employee.getId()+"   "+ employee.getName() +"   "
                    + employee.residualDayOff);

            log.addLog("[연차정보 조회] " ,
                    "사원번호 :"+employee.getId()+
                            " 사원이름 :"+employee.getName()+
                            " 연봉 :" + salary+
                            " 잔여연차 :"+employee.getResidualDayOff());
            UI.print2(Langs.DAY_OFF_INFO_PAGE);
            while (true) {
                String menu = UI.getInput();
                if (menu.equals("1")) {
                    UI.print(employee.getName()+"님의 연차 초과사용으로 인한 차감액은 "
                            +Utils.floatToString(
                                    dayOffInfoService.cal(
                                            employee.getSalary().floatValue(),
                                            employee.getResidualDayOff()
                                    ))+"원 입니다");
                    log.addLog("[차감액 조회] " , "사원번호 :"+employee.getId()+
                            " 사원이름 :"+employee.getName()+
                            " 연봉 :" + salary+
                            " 잔여연차 :"+employee.getResidualDayOff() +
                            " 차감액 :"+ Utils.floatToString(
                                    dayOffInfoService.cal(
                                            employee.getSalary().floatValue(),
                                            employee.getResidualDayOff())));
                    return new MainController();
                } else if (menu.equals("2")) {
                    return new MainController();
                } else {
                    Utils.pause("올바르지 않은 입력입니다. 다시 입력해주세요");
                    UI.print2("입력:");
                }
            }
        }
        return new MainController();
    }

}