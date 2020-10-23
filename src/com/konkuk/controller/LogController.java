package com.konkuk.controller;


import com.konkuk.service.UI;
import com.konkuk.asset.Langs;
import com.konkuk.dto.Log;
import com.konkuk.service.LogService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;

public class LogController extends Controller {
    public enum Menu {LOG}
    protected Menu currentMenu;

    public LogController(Menu menu) {
        this.currentMenu = menu;
    }

    public Controller start() {
        if (currentMenu == Menu.LOG) {
            try {
                LogCheck();
            } catch(InterruptedException e) {
                e.printStackTrace();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        // 수정 필요
        return new MainController();
    }

    private void Print_Log() {
        List<Log> log_list = (new LogService()).getLogs();
        UI.print(Langs.LOG_TITLE);
        UI.print(Langs.HORIZON);
        log_list.forEach((log -> UI.print(log.Day + " " + log.log_category + " " + log.log_content)));
        UI.print(Langs.HORIZON);
        UI.print(Langs.INSERT_BACK);
    }


    public void LogCheck() throws IOException, InterruptedException {
        while(true) {
            Print_Log();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String confirm;
            confirm = br.readLine();
            if(confirm.equals("B") || confirm.equals("b")) {
                break;
            }
            else {
                System.out.print("올바르지 않은 입력입니다. 다시 입력해주세요");
                try {
                    Thread.sleep(2000);
                    System.out.print("\033[H\033{2J");
                    System.out.flush();
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

