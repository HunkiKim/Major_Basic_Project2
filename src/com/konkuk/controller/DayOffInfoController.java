package com.konkuk.controller;

import com.konkuk.UI;
import com.konkuk.asset.Langs;
import com.konkuk.dto.Employee;
import com.konkuk.repository.EmployeeRepository;
import com.konkuk.repository.LogRepository;
import com.konkuk.service.DayOffInfoService;


public class DayOffInfoController extends Controller {
    LogRepository log = LogRepository.getInstance();
    DayOffInfoService dayOffInfoService= new DayOffInfoService();

    int employeeId;



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


        if (employeeRepository.findByExactId(employeeId).getResidualDayOff() == 0) {  //잔여연차=0
            UI.print2(Langs.HORIZON);
            UI.print(Langs.DAY_OFF_INFO);
            UI.print2(Langs.HORIZON);
            UI.print( employeeRepository.findByExactId(employeeId).getId()+"   "+ employeeRepository.findByExactId(employeeId).getName() +"   "
                    + 0);
            UI.print("\n연차를 모두 사용하였습니다. [메인 화면]으로 이동합니다.");
            log.addLog("[연차정보 조회] " ,
                    "사원번호 :"+employeeRepository.findByExactId(employeeId).getId()+
                            " 사원이름 :"+employeeRepository.findByExactId(employeeId).getName()+
                            " 연봉 :" + employeeRepository.findByExactId(employeeId).getSalary()+
                            " 잔여연차 :"+employeeRepository.findByExactId(employeeId).getResidualDayOff());
        }
        else if (employeeRepository.findByExactId(employeeId).getResidualDayOff() >= 1) { //잔여연차 1 이상
            UI.print2(Langs.HORIZON);
            UI.print(Langs.DAY_OFF_INFO);
            UI.print2(Langs.HORIZON);
            UI.print( employeeRepository.findByExactId(employeeId).getId()+"   "+ employeeRepository.findByExactId(employeeId).getName() +"   "
                    + employeeRepository.findByExactId(employeeId).getResidualDayOff());

            log.addLog("[연차정보 조회] " ,
                    "사원번호 :"+employeeRepository.findByExactId(employeeId).getId()+
                            " 사원이름 :"+employeeRepository.findByExactId(employeeId).getName()+
                            " 연봉 :" + employeeRepository.findByExactId(employeeId).getSalary()+
                            " 잔여연차 :"+employeeRepository.findByExactId(employeeId).getResidualDayOff());
            UI.print2(Langs.DAY_OFF_INFO_PAGE_REFUND);
            while (true) { //1.환급액 조회 2.메인메뉴 이동
                String menu = UI.getInput();
                if (menu.equals("1")) {
                    UI.print(employeeRepository.findByExactId(employeeId).getName()+"님의 연차 미사용으로 인한 환급액은 "
                            +dayOffInfoService.refund_cal(employeeRepository.findByExactId(employeeId).getSalary(),employeeRepository.findByExactId(employeeId).getResidualDayOff(),
                            employeeRepository.findByExactId(employeeId).getName())+"원 입니다");
                    log.addLog("[환급액 조회] " , "사원번호 :"+employeeRepository.findByExactId(employeeId).getId()+
                            " 사원이름 :"+employeeRepository.findByExactId(employeeId).getName()+
                            " 연봉 :" + employeeRepository.findByExactId(employeeId).getSalary()+
                            " 잔여연차 :"+employeeRepository.findByExactId(employeeId).getResidualDayOff()+
                            " 환급액 :"+dayOffInfoService.refund_cal(employeeRepository.findByExactId(employeeId).getSalary(),
                             employeeRepository.findByExactId(employeeId).getResidualDayOff(),
                            employeeRepository.findByExactId(employeeId).getName()));
                    return new MainController();
                } else if (menu.equals("2")) {
                    return new MainController();}
                else {
                    UI.print("올바르지 않은 입력입니다. 다시 입력해주세요");
                    try {
                        Thread.sleep(2000);
                        UI.print2("입력:");
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                } }}

        else if (employeeRepository.findByExactId(employeeId).getResidualDayOff() < 0) { //잔여연차 0미만
            UI.print2(Langs.HORIZON);
            UI.print(Langs.DAY_OFF_INFO);
            UI.print2(Langs.HORIZON);
            UI.print( employeeRepository.findByExactId(employeeId).getId()+"   "+ employeeRepository.findByExactId(employeeId).getName() +"   "
                    + 0);

            log.addLog("[연차정보 조회] " ,
                    "사원번호 :"+employeeRepository.findByExactId(employeeId).getId()+
                            " 사원이름 :"+employeeRepository.findByExactId(employeeId).getName()+
                            " 연봉 :" + employeeRepository.findByExactId(employeeId).getSalary()+
                            " 잔여연차 :"+employeeRepository.findByExactId(employeeId).getResidualDayOff());
            UI.print2(Langs.DAY_OFF_INFO_PAGE);
            while (true) {
                String menu = UI.getInput();
                if (menu.equals("1")) {
                    UI.print(employeeRepository.findByExactId(employeeId).getName()+"님의 연차 초과사용으로 인한 차감액은 "
                            +dayOffInfoService.cal(employeeRepository.findByExactId(employeeId).getSalary(),employeeRepository.findByExactId(employeeId).getResidualDayOff(),
                            employeeRepository.findByExactId(employeeId).getName())+"원 입니다");
                    log.addLog("[차감액 조회] " , "사원번호 :"+employeeRepository.findByExactId(employeeId).getId()+
                            " 사원이름 :"+employeeRepository.findByExactId(employeeId).getName()+
                            " 연봉 :" + employeeRepository.findByExactId(employeeId).getSalary()+
                            " 잔여연차 :"+employeeRepository.findByExactId(employeeId).getResidualDayOff() +
                            " 차감액 :"+dayOffInfoService.cal(employeeRepository.findByExactId(employeeId).getSalary(),employeeRepository.findByExactId(employeeId).getResidualDayOff(),
                            employeeRepository.findByExactId(employeeId).getName()));
                    return new MainController();
                } else if (menu.equals("2")) {
                    return new MainController();
                } else {
                    UI.print("올바르지 않은 입력입니다. 다시 입력해주세요");
                    try {
                        Thread.sleep(2000);
                        UI.print2("입력:");
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return new MainController();
    }

}