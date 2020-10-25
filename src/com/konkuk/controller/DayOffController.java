package com.konkuk.controller;

import com.konkuk.Main;
import com.konkuk.UI;
import com.konkuk.Utils;
import com.konkuk.asset.Langs;
import com.konkuk.dto.DayOff;
import com.konkuk.dto.Employee;
import com.konkuk.repository.DayOffRepository;
import com.konkuk.repository.EmployeeRepository;
import com.konkuk.service.DayOffService;
import com.konkuk.service.DayOffService.DayOffType;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DayOffController extends Controller {
    int employeeId;

    public DayOffController(int employeeId) {
        this.employeeId = employeeId;
    }

    private int type = 0;
    private String start = null;
    private String start1 = null;
    private String end = null;
    private String reason = null;
    private float count = 0;

    EmployeeRepository employeeRepository = EmployeeRepository.getInstance();
    Employee employee = employeeRepository.findByExactId(employeeId);
    DayOff dayOff = null;

    public Controller start() {
        UI.print(Langs.DAY_OFF_MAIN);
        String menu = UI.getInput();
        while(true) {
            if(menu.equals("1")) {
                use();
                break;
            } else if(menu.equals("2")) {
                add();
                break;
            } else if(menu.equals("3")) {
                change_cancel();
                break;
            } else if(menu.equals("4")) {
                reduct();
                break;
            } else {
                UI.print(Langs.INPUT_ERROR);
            }
        }
        return new MainController();
    }


    private void use() {
        DayOffService dayOffService = new DayOffService();

        while (true) {
            UI.print(Langs.DAY_OFF_USE);
            String menu = UI.getInput();

            if (menu.equals("1")) {         //연차
                type = 0;
                break;
            } else if (menu.equals("2")) {         //반차
                type = 1;
                break;
            } else {
                UI.print(Langs.INPUT_ERROR);
            }

        }

        while(true){
            UI.print(Langs.DAY_OFF_REASON);
            reason = UI.getInput();

            if(dayOffService.reasonCheck(reason)==true){
                break;
            }
            else continue;
        }

        while(true){
            UI.print(Langs.DAY_OFF_START);
            start = UI.getInput();

            Date startDate = Utils.stringToDate(start);

            dayOff = DayOffRepository.getInstance().findByDate(employeeId, startDate);  //이미 사용한 날짜를 중복해서 입력 예외
            if(dayOff!=null){
                System.out.print(Langs.DAY_OFF_USED);
                continue;
            }

            if(startDate == null) {
                UI.print(Langs.INPUT_ERROR_TIME);
            } else {
                long endTime = startDate.getTime() + (type == 0 ? 28800000 : 14400000);

                Calendar tmpStart = Calendar.getInstance();
                Calendar tmpEnd = Calendar.getInstance();
                tmpStart.setTime(startDate);
                tmpEnd.setTime(new Date(endTime));
                if(tmpStart.get(Calendar.HOUR_OF_DAY) < 12 && tmpEnd.get(Calendar.HOUR_OF_DAY) > 12) {
                    endTime += 3600000;
                }
                end = Utils.dateToString(new Date(endTime));
                break;
            }
        }

        DayOffType dayOffType = type == 1 ? DayOffType.AllDay : DayOffType.HalfDay;
        Employee employee = dayOffService.use(employeeId, dayOffType, reason, start, end);
        if (employee != null) {
            //결과 출력
            UI.print(Langs.DATA_FILE_HEADER_DAYOFF_RESULT);
            UI.print(Langs.HORIZON);
            String result1 = employee.id + " " +
                    employee.name + " " +
                    reason + " " +
                    start + " " +
                    end + " " +
                    employee.residualDayOff;
            UI.print(result1);
        } else {
            // 실패한 것
            UI.print(Langs.DAY_OFF_ERROR);
        }
    }

    private void add() {
        DayOffService dayOffService = new DayOffService();

        while(true){
            UI.print(Langs.DAY_OFF_REASON);
            reason = UI.getInput();

            if(dayOffService.reasonCheck(reason)==true){
                break;
            }
            else continue;
        }

        while(true){
            UI.print(Langs.DAY_OFF_ADD);
            count = UI.getInput1();

            if(dayOffService.countCheck(count)==true){
                break;
            }
            else continue;
        }

        boolean isDone = dayOffService.add(employee, reason, count);

        if(isDone) {
            //결과 출력
            UI.print(Langs.DATA_FILE_HEADER_DAYOFF_RESULT2);
            UI.print(Langs.HORIZON);
            String result2 = employee.id + " " +
                            employee.name + " " +
                            employee.residualDayOff;
            UI.print(result2);
        } else {
            // 실패한 것
            UI.print(Langs.DAY_OFF_ERROR);
        }
    }

    private void change_cancel() {
        int m = 0;
        DayOffService dayOffService = new DayOffService();

        //연차 사용 리스트 출력
        UI.print(Langs.HORIZON);
        UI.print(Langs.DATA_FILE_HEADER_DAYOFF_RESULT3);
        UI.print(Langs.HORIZON);
        List<String> dayOffList = dayOffService.getList(employeeId);
        for(String data : dayOffList){
            System.out.println(data);
        }

        while(true){
            UI.print(Langs.DAY_OFF_CC);
            String menu = UI.getInput();

            if(menu.equals("1")){
                m = 1;
                break;
            } else if(menu.equals("2")){
                m = 2;
                break;
            } else{
                UI.print(Langs.INPUT_ERROR);
                continue;
            }
        }

        if(m==1){       //수정
            while(true){    //연차번호 검색, 찾기
                UI.print(Langs.INPUT_NUM);
                int num = UI.getInput2();

                dayOff = DayOffRepository.getInstance().findByExactId(num);
                if(dayOff==null){
                    System.out.print(Langs.DAY_OFF_NOT_EXIST);
                    continue;
                } else break;
            }

            while(true){
                UI.print(Langs.DAY_OFF_CHANGE_REASON);
                String reason1 = UI.getInput();
                if(reason1 == "p" || reason1 == "P") {    //건너뛰기
                    reason = dayOff.reason;
                    break;
                }
                if(dayOffService.reasonCheck(reason)==true){
                    break;
                }
                else continue;
            }

            while(true){
                UI.print(Langs.DAY_OFF_CHANGE_START);
                start1 = UI.getInput();

                Date startDate = Utils.stringToDate(start);

                dayOff = DayOffRepository.getInstance().findByDate(employeeId, startDate);  //이미 사용한 날짜를 중복해서 입력 예외
                if(dayOff!=null){
                    System.out.print(Langs.DAY_OFF_USED2);
                    continue;
                }

                if(startDate == null) {
                    UI.print(Langs.INPUT_ERROR_TIME);
                } else {
                    if(start1 == "p" || start1 == "P"){
                        start = Utils.dateToString(dayOff.dateDayOffStart);
                        end = Utils.dateToString(dayOff.dateDayOffEnd);
                        break;
                    } else{
                        long endTime = startDate.getTime() + 14400000;  //연차 수정은 4시간 고정

                        Calendar tmpStart = Calendar.getInstance();
                        Calendar tmpEnd = Calendar.getInstance();
                        tmpStart.setTime(startDate);
                        tmpEnd.setTime(new Date(endTime));
                        if(tmpStart.get(Calendar.HOUR_OF_DAY) < 12 && tmpEnd.get(Calendar.HOUR_OF_DAY) > 12) {
                            endTime += 3600000;
                        }
                        end = Utils.dateToString(new Date(endTime));
                        break;
                    }
                }
            }

            boolean isDone = dayOffService.change(dayOff, reason, start, end);

                if (isDone) {
                    //출력
                    UI.print(Langs.DATA_FILE_HEADER_DAYOFF_RESULT3);
                    UI.print(Langs.HORIZON);
//                    String result3 = dayOff.id + " " +
//                            dayOff.employeeId + " " +
//                            dayOff + " " +
//                            reason + " " +
//                            start + " " +
//                            end + " " +
//                            dayOff.getResidualDayOff();
//                    UI.print(result3);
                } else {
                    // 실패한 것
                    UI.print(Langs.DAY_OFF_ERROR);
                }

        } else if (m==2){       //취소
            while(true){    //연차번호 검색, 찾기
                UI.print(Langs.INPUT_NUM);
                int num = UI.getInput2();

                dayOff = DayOffRepository.getInstance().findByExactId(num);
                if(dayOff==null){
                    System.out.print(Langs.DAY_OFF_NOT_EXIST);
                    continue;
                } else break;
            }

            boolean isDone = dayOffService.cancel(dayOff);

            if (isDone) {
                //잘 된것
                UI.print(Langs.DAY_OFF_DELETE);
            } else {
                // 실패한 것
                UI.print(Langs.DAY_OFF_ERROR);
            }
        }

    }


    private void reduct(){
        DayOffService dayOffService = new DayOffService();

        while(true){
            UI.print(Langs.DAY_OFF_REASON);
            reason = UI.getInput();

            if(dayOffService.reasonCheck(reason)==true){
                break;
            }
            else continue;
        }

        while(true){
            UI.print(Langs.DAY_OFF_RED);
            count = UI.getInput1();

            if(dayOffService.countCheck(count)==true){
                break;
            }
            else continue;
        }

        boolean isDone = dayOffService.reduct(employee, reason, count);

        if(isDone) {
            //출력
            UI.print(Langs.DATA_FILE_HEADER_DAYOFF_RESULT2);
            UI.print(Langs.HORIZON);
            String result2 = employee.id + " " +
                            employee.name + " " +
                            employee.residualDayOff;
            UI.print(result2);
        } else {
            // 실패한 것
            UI.print(Langs.DAY_OFF_ERROR);
        }
    }
}
