package com.konkuk.controller;

import com.konkuk.Main;
import com.konkuk.UI;
import com.konkuk.asset.Langs;
import com.konkuk.dto.DayOff;
import com.konkuk.dto.Employee;
import com.konkuk.service.DayOffService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DayOffController extends Controller {
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
        int type;
        String end = null;

        UI.print(Langs.DAY_OFF_USE);
        String menu = UI.getInput();

        UI.print(Langs.DAY_OFF_REASON);
        String reason = UI.getInput();
        UI.print(Langs.DAY_OFF_START);
        String start = UI.getInput();

        //시간 입력 형태
        SimpleDateFormat formatter = new SimpleDateFormat("YYYYMMDD HH:MM");

        DayOffService dayOffService = new DayOffService();

        // 오류나서 일단 넣어놨어여
        Employee employee = null;
        while (true) {
            if (menu.equals("1")) {         //연차
                type = 0;

                try{
                    //시작시간
                    Date st_date = formatter.parse(start);
                    //종료시간
                    long end1 = st_date.getTime() + 28800000;      //8시간
                    end = formatter.format(new Date(end1));

                } catch (ParseException e){
                    System.out.println(Langs.INPUT_ERROR_TIME);
                }

                boolean isDone = dayOffService.use(employee, type, reason, start, end);

                if (isDone) {
                    //결과 출력

                } else {
                    // 실패한 것
                    UI.print("실패 메시지, Langs에 넣");
                }
                break;
            } else if (menu.equals("2")) {         //반차
                type = 1;
                //SimpleDateFormat formatter = new SimpleDateFormat("yyyymmdd hh:mm");
                try{
                    //시작시간
                    Date st_date = formatter.parse(start);
                    //종료시간
                    long end2 = st_date.getTime() + 14400000;      //4시간
                    end = formatter.format(new Date(end2));
                } catch (ParseException e){
                    System.out.println(Langs.INPUT_ERROR_TIME);
                }

                boolean isDone = dayOffService.use(employee, type, reason, start, end);
                if (isDone) {
                    //결과 출력

                } else {
                    // 실패한 것
                    UI.print("실패 메시지, Langs에 넣");
                }
                break;
            } else {
                UI.print(Langs.INPUT_ERROR);
            }
        }
    }

    private void add() {
        UI.print(Langs.DAY_OFF_REASON);
        String reason = UI.getInput();
        UI.print(Langs.DAY_OFF_ADD);
        int count = UI.getInput1();


        // 오류나서 일단 넣어놨어여
        Employee employee = null;
        DayOffService dayOffService = new DayOffService();
        boolean isDone = dayOffService.add(employee, reason, count);

        if(isDone) {
            //결과 출력
        } else {
            // 실패한 것
            UI.print("실패 메시지, Langs에 넣");
        }
    }

    private void change_cancel() {
        String reason = null;
        String start = null;
        String end = null;

        UI.print(Langs.DAY_OFF_CC);
        String menu = UI.getInput();

        //연차 사용 리스트 출력
        UI.print(Langs.DATA_FILE_HEADER_DAYOFF_USE);



        DayOffService dayOffService = new DayOffService();
        UI.print(Langs.INPUT_NUM);
        int input_num = UI.getInput1();

        DayOff dayOff = new DayOff();       //연차번호에 해당하는 dayOff객체 찾기

        while (true) {
            if (menu.equals("1")) {       //수정
                UI.print(Langs.DAY_OFF_CHANGE_REASON);
                String reason1 = UI.getInput();
                if(reason1 == "p" || reason1 == "P") {    //건너뛰기
                    reason = dayOff.getReason();
                } else {
                    reason = reason1;
                }
                UI.print(Langs.DAY_OFF_CHANGE_START);
                String start1 = UI.getInput();
                if(start1 == "p" || start1 == "P"){   //건너뛰기
                    start = dayOff.getStart();
                    end = dayOff.getEnd();
                } else {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyymmdd hh:mm");
                    try{
                        //시작시간
                        Date st_date = formatter.parse(start1);
                        //종료시간
                        long end3 = st_date.getTime() + 14400000;      //4시간
                        end = formatter.format(new Date(end3));
                    } catch (ParseException e){
                        System.out.println(Langs.INPUT_ERROR_TIME);
                    }
                }

                boolean isDone = dayOffService.change(dayOff, reason, start, end);

                if (isDone) {
                    //잘 된것
                    // 다음처리

                } else {
                    // 실패한 것
                    UI.print("실패 메시지, Langs에 넣");
                }
                break;
            } else if (menu.equals("2")) {        //취소

                boolean isDone = dayOffService.cancel(dayOff);

                if (isDone) {
                    //잘 된것
                    UI.print(Langs.DAY_OFF_DELETE);
                } else {
                    // 실패한 것
                    UI.print("실패 메시지, Langs에 넣");
                }
                break;
            } else {
                UI.print(Langs.INPUT_ERROR);
            }

        }
    }


    private void reduct(){
        UI.print(Langs.DAY_OFF_REASON);
        String reason = UI.getInput();
        UI.print(Langs.DAY_OFF_RED);
        float count = UI.getInput1();

        DayOffService dayOffService = new DayOffService();
        // 얘도 오류낫 ㅓ일단 넣었어여
        Employee employee = null;
        boolean isDone = dayOffService.reduct(employee, reason, count);

        if(isDone) {
            //잘 된것
            // 다음처리
        } else {
            // 실패한 것
            UI.print("실패 메시지, Langs에 넣");
        }
    }
}
