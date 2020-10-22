package com.konkuk.controller;

import com.konkuk.Main;
import com.konkuk.UI;
import com.konkuk.asset.Langs;
import com.konkuk.dto.DayOff;
import com.konkuk.service.DayOffService;

import static com.konkuk.asset.Langs.DAY_OFF_CC;

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


    private void use(){
        UI.print(Langs.DAY_OFF_USE);
        String menu = UI.getInput();

        UI.print(Langs.DAY_OFF_REASON);
        String reason = UI.getInput();
        UI.print(Langs.DAY_OFF_START);
        String start = UI.getInput();

        //시작시간 확인
        int y = Integer.parseInt(start.substring(0,4));
        int m = Integer.parseInt(start.substring(4,6));
        int s = Integer.parseInt(start.substring(6,8));

        DayOffService dayOffService = new DayOffService();
        // 타입에는 반차인지 연차인지. 일단은 그냥 int로 해놨어요

        while (true) {
            if (menu.equals("1")) {       //수정
                //종료시간


                boolean isDone = dayOffService.use(employee, reason, start, end);

                if (isDone) {
//            잘 된것
                    // 다음처리
                } else {
                    // 실패한 것
                    UI.print("실패 메시지, Langs에 넣");
                }

            } else if (menu.equals("2")) {        //취소
                //종료시간


                boolean isDone = dayOffService.use(employee, reason, start, end);
                if (isDone) {
                    //잘 된것
                    // 다음처리
                } else {
                    // 실패한 것
                    UI.print("실패 메시지, Langs에 넣");
                }

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


        DayOffService dayOffService = new DayOffService();
        boolean isDone = dayOffService.add(employee, reason, count);

        if(isDone) {
//            잘 된것
            // 다음처리
        } else {
            // 실패한 것
            UI.print("실패 메시지, Langs에 넣");
        }
    }

    private void change_cancel() {
        UI.print(Langs.DAY_OFF_CC);
        String menu = UI.getInput();

        //연차 사용 리스트 출력

        DayOffService dayOffService = new DayOffService();
        UI.print(Langs.INPUT_NUM);
        int input_num = UI.getInput1();

        DayOff dayOff = new DayOff();       //연차번호에 해당하는 dayOff객체 찾기

        while (true) {
            if (menu.equals("1")) {       //수정
                UI.print(Langs.DAY_OFF_CHANGE_REASON);
                String reason = UI.getInput();
                UI.print(Langs.DAY_OFF_CHANGE_START);
                String start = UI.getInput();
                //시작시간 확인


                //종료시간


                boolean isDone = dayOffService.change(dayOff, reason, start, end);

                if (isDone) {
//            잘 된것
                    // 다음처리
                } else {
                    // 실패한 것
                    UI.print("실패 메시지, Langs에 넣");
                }

            } else if (menu.equals("2")) {        //취소

                boolean isDone = dayOffService.cancel(dayOff);
                if (isDone) {
                    //잘 된것
                    // 다음처리
                } else {
                    // 실패한 것
                    UI.print("실패 메시지, Langs에 넣");
                }

            } else {
                UI.print(Langs.INPUT_ERROR);
            }

        }
    }


    private void reduct(){
        UI.print(Langs.DAY_OFF_REASON);
        String reason = UI.getInput();
        UI.print(Langs.DAY_OFF_RED);
        int count = UI.getInput1();

        DayOffService dayOffService = new DayOffService();
        boolean isDone = dayOffService.reduct(employee, reason, count);

        if(isDone) {
//            잘 된것
            // 다음처리
        } else {
            // 실패한 것
            UI.print("실패 메시지, Langs에 넣");
        }
    }
}
