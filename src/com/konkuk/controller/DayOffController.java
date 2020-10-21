package com.konkuk.controller;

import com.konkuk.Main;
import com.konkuk.UI;
import com.konkuk.asset.Langs;
import com.konkuk.service.DayOffService;

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
            } else {
                UI.print(Langs.INPUT_ERROR);
            }
        }
        return new MainController();
    }

    private void add() {

    }

    private void use(){
        UI.print(Langs.DAY_OFF_USE);
        String menu = UI.getInput();

        UI.print(Langs.DAY_OFF_REASON);
        String reason = UI.getInput();
        UI.print(Langs.DAY_OFF_START);
        String startDate = UI.getInput();

        DayOffService dayOffService = new DayOffService();
        // 타입에는 반차인지 연차인지. 일단은 그냥 int로 해놨어요
        boolean isDone = dayOffService.use(0, reason, startDate);
        if(isDone) {
//            잘 된것
            // 다음처리
        } else {
            // 실패한 것
            UI.print("실패 메시지, Langs에 넣");
        }
    }
}
