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
import com.konkuk.service.EmployeeService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DayOffController extends Controller {
    int employeeId;

    public DayOffController(int employeeId) {
        this.employeeId = employeeId;
    }

    EmployeeRepository employeeRepository = EmployeeRepository.getInstance();
    EmployeeService employeeService = new EmployeeService();

    public Controller start() {
        Employee employee = employeeRepository.findByExactId(employeeId);
        System.out.println();
        UI.print(Langs.DATA_FILE_HEADER_DAYOFF_RESULT2);
        UI.print(Langs.HORIZON);
        String result2 = employee.id + " " +
                employee.name + " " +
                employee.residualDayOff;
        UI.print(result2);

        while(true) {
            UI.print2(Langs.DAY_OFF_MAIN);
            String menu = UI.getInput();

            if(menu.equals("B") || menu.equals("b")){
                return new MainController();
            }
            else if (menu.equals("1")) {
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
                Utils.pause(Langs.BLANK_SPACE_ERROR);
                continue;
            }
        }
        return new MainController();
    }

    private void use() {
        int type;
        String reason, start, end;
        DayOff dayOff;

        DayOffService dayOffService = new DayOffService();

        while (true) {
            UI.print2(Langs.DAY_OFF_USE);
            String menu = UI.getInput();

            if (menu.equals("1")) {         //연차
                type = 0;
                break;
            } else if (menu.equals("2")) {         //반차
                type = 1;
                break;
            } else {
                Utils.pause(Langs.BLANK_SPACE_ERROR);
            }

        }

        while(true){
            UI.print2(Langs.DAY_OFF_REASON);
            reason = UI.getInput();

            if(dayOffService.reasonCheck(reason)==true){
                break;
            }
            else continue;
        }

        while(true){
            UI.print2(Langs.DAY_OFF_START);
            start = UI.getInput();

            Date startDate = Utils.stringToDate(start);
            if(startDate == null) {
                Utils.pause(Langs.INPUT_ERROR_TIME);
                continue;
            }
            dayOff = DayOffRepository.getInstance().findByDate(employeeId, startDate);  //이미 사용한 날짜를 중복해서 입력 예외
            if(dayOff!=null){
                Utils.pause(Langs.DAY_OFF_USED);
                continue;
            }
            long endTime = startDate.getTime() + (type == 0 ? 28800000 : 14400000);

            Calendar tmpStart = Calendar.getInstance();
            Calendar tmpEnd = Calendar.getInstance();
            tmpStart.setTime(startDate);
            tmpEnd.setTime(new Date(endTime));
            if(tmpStart.get(Calendar.HOUR_OF_DAY) <= 12 && tmpEnd.get(Calendar.HOUR_OF_DAY) >= 12) {
                endTime += 3600000;
            }
            end = Utils.dateToString(new Date(endTime));
            break;
        }

        DayOffType dayOffType = type == 0 ? DayOffType.AllDay : DayOffType.HalfDay;
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
            Utils.pause(Langs.DAY_OFF_ERROR);
        }
    }

    private void add() {
        String reason;
        float count;

        DayOffService dayOffService = new DayOffService();

        while(true){
            UI.print2(Langs.DAY_OFF_ADD_REASON);
            reason = UI.getInput();

            if(dayOffService.reasonCheck(reason)==true){
                break;
            }
            else continue;
        }

        while(true){
            UI.print2(Langs.DAY_OFF_ADD);
            count = UI.getInput1();

            if(dayOffService.countCheck(count)==true){
                break;
            }
            else continue;
        }

        Employee employee = dayOffService.add(employeeId, reason, count);
        if(employee == null) {
            Utils.pause(Langs.DAY_OFF_ERROR);
        } else {
            UI.print(Langs.DATA_FILE_HEADER_DAYOFF_RESULT2);
            UI.print(Langs.HORIZON);
            String result2 = employee.id + " " +
                    employee.name + " " +
                    employee.residualDayOff;
            UI.print(result2);
        }
    }

    private void change_cancel() {
        int num = 0;
        String reason, start1, start = "", end;

        DayOff dayOff = null;
        DayOff dayOff2 = null;
        int m = 0;
        DayOffService dayOffService = new DayOffService();

        //연차 사용 리스트 출력
        UI.print(Langs.HORIZON);
        UI.print(Langs.DATA_FILE_HEADER_DAYOFF_RESULT3);
        UI.print(Langs.HORIZON);
        List<String> dayOffList = dayOffService.getUsedDayOffList(employeeId);
        for(String data : dayOffList){
            System.out.println(data);
        }

        while(true){
            UI.print2(Langs.DAY_OFF_CC);
            String menu = UI.getInput();

            if(menu.equals("1")){
                m = 1;
                break;
            } else if(menu.equals("2")){
                m = 2;
                break;
            } else{
                Utils.pause(Langs.INPUT_ERROR);
                continue;
            }
        }

        if(m==1){       //수정
            while(true){    //연차번호 검색, 찾기
                UI.print2(Langs.INPUT_NUM);
                num = UI.getInput2();

                dayOff = DayOffRepository.getInstance().findByExactId(num);
                if(dayOff==null){
                    Utils.pause(Langs.DAY_OFF_NOT_EXIST);
                } else break;
            }

            while(true){
                UI.print2(Langs.DAY_OFF_CHANGE_REASON);
                String reason1 = UI.getInput();
                if(reason1.equals("p") || reason1.equals("P")) {    //건너뛰기
                    reason = dayOff.reason;
                    break;
                } else{
                    if(dayOffService.reasonCheck(reason1)){
                        reason = reason1;
                        break;
                    }
                }
            }

            while(true){
                UI.print2(Langs.DAY_OFF_CHANGE_START);
                start1 = UI.getInput();

                Date startDate = Utils.stringToDate(start1);
                if(startDate == null) {
                    Utils.pause(Langs.INPUT_ERROR_TIME);
                    continue;
                }

                if(start1.equals("p") || start1.equals("P")){
                    start = Utils.dateToString(dayOff.dateDayOffStart);
                    end = Utils.dateToString(dayOff.dateDayOffEnd);
                    dayOff = dayOffService.change(num, reason, start, end);
                    break;
                }

                dayOff2 = DayOffRepository.getInstance().findByDate(employeeId, startDate);  //이미 사용한 날짜를 중복해서 입력 예외
                if(dayOff2!=null){
                    Utils.pause(Langs.DAY_OFF_USED2);
                    continue;
                }


                long endTime = startDate.getTime() + 14400000;  //연차 수정은 4시간 고정

                Calendar tmpStart = Calendar.getInstance();
                Calendar tmpEnd = Calendar.getInstance();
                tmpStart.setTime(startDate);
                tmpEnd.setTime(new Date(endTime));
                if(tmpStart.get(Calendar.HOUR_OF_DAY) <= 12 && tmpEnd.get(Calendar.HOUR_OF_DAY) >= 12) {
                    endTime += 3600000;
                }
                end = Utils.dateToString(new Date(endTime));
                dayOff = dayOffService.change(num, reason, start1, end);
                break;

            }

            Employee employee = employeeService.getEmployee(employeeId);
            String start3 = Utils.dateToString(dayOff.dateDayOffStart);
            if (dayOff!=null) {
                //출력
                UI.print(Langs.DATA_FILE_HEADER_DAYOFF_RESULT3);
                UI.print(Langs.HORIZON);
                String result3 = dayOff.id + " " +
                        dayOff.employeeId + " " +
                        employee.name + " " +
                        reason + " " +
                        start3 + " " +
                        end + " " +
                        employee.residualDayOff;
                UI.print(result3);
            } else {
                // 실패한 것
                Utils.pause(Langs.DAY_OFF_ERROR);
            }
        } else {       //취소
            String inputs;
            int dayOffId = 0;
            while(true){    //연차번호 검색, 찾기
                UI.print2(Langs.INPUT_NUM);
                inputs = UI.getInput();
                if(Utils.isOnlyNumber(inputs)) {
                    dayOffId = Integer.parseInt(inputs);
                    if(dayOffService.getDayOff(dayOffId) == null) {
                        Utils.pause(Langs.DAY_OFF_NOT_EXIST);
                    } else {
                        break;
                    }
                } else {
                    Utils.pause(Langs.BLANK_SPACE_ERROR);
                }
            }
            boolean isDone = dayOffService.cancel(dayOffId);

            if (isDone) {
                //잘 된것
                UI.print(Langs.DAY_OFF_DELETE);
            } else {
                // 실패한 것
                Utils.pause(Langs.DAY_OFF_ERROR);
            }
        }

    }


    private void reduct(){
        String reason;
        float count;

        DayOffService dayOffService = new DayOffService();

        while(true){
            UI.print2(Langs.DAY_OFF_RED_REASON);
            reason = UI.getInput();

            if(dayOffService.reasonCheck(reason)){
                break;
            }
        }

        while(true){
            UI.print2(Langs.DAY_OFF_RED);
            count = UI.getInput1();

            if(dayOffService.countCheck(count)){
                break;
            }
        }

        Employee employee = dayOffService.reduct(employeeId, reason, count);
        if(employee == null) {
            Utils.pause(Langs.DAY_OFF_ERROR);
        } else {
            UI.print(Langs.DATA_FILE_HEADER_DAYOFF_RESULT2);
            UI.print(Langs.HORIZON);
            String result2 = employee.id + " " +
                    employee.name + " " +
                    employee.residualDayOff;
            UI.print(result2);
        }
    }
}
