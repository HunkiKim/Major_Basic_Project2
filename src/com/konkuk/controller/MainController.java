package com.konkuk.controller;

import com.konkuk.UI;
import com.konkuk.asset.Langs;

public class MainController extends Controller {
    public Controller start() {
        Controller next = null;
        while(true) {
            String nextMenu = UI.getInput(Langs.MAIN);
            switch (nextMenu) {
                case "1":
                    next = new MemberController(MemberController.Menu.ADD);
                case "2":
                    next = new MemberController(MemberController.Menu.FIND);
                default:
                    break;
            }
            if (next != null) break;
            // 그 외는 공통기 메시지 출력 후 다시 받기
        }
        return next;
    }
}
