package com.konkuk.controller;


import com.konkuk.UI;
import com.konkuk.asset.Langs;
import com.konkuk.dto.Log;
import com.konkuk.repository.LogRepository;
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
        return new MainController();
    }

    private void Print_Log() {
        List<Log> log_list = (new LogService()).getLogs();
        UI.print(Langs.LOG_TITLE);
        UI.print2(Langs.HORIZON);
        log_list.forEach((log -> UI.print(log.Day.substring(0, 4) + "/" + log.Day.substring(4, 6) + "/" + log.Day.substring(6,14) + " " + log.log_category + log.log_content)));
        UI.print2(Langs.HORIZON);

    }


    public void LogCheck() throws IOException, InterruptedException {
        Print_Log();
        while(true) {
            UI.print2(Langs.INSERT_BACK);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String confirm;
            confirm = br.readLine();
            if(confirm.equals("B") || confirm.equals("b")) {
                break;
            }
            else {
                UI.print("올바르지 않은 입력입니다. 다시 입력해주세요");
                try {
                    Thread.sleep(2000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

