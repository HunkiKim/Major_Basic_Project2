package com.konkuk.controller;


import com.konkuk.service.LogService;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

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

    private static void Print_Log() {
        List<String> log_list = LogService.get_log();
        System.out.println("[로그내역]");
        System.out.println("-----------------------------------------------------------------------------------------");
        for (int i=0; i<log_list.size(); i += 4) {
            String s = log_list.get(i+3);
            String[] arr;
            arr = s.split("");
            for (int j = 0; j < arr.length; j++) {
                System.out.print(arr[j]);
                if (j == 3 || j == 5)
                {
                    System.out.print("/");
                }
            }
            System.out.print(" " + log_list.get(i+1) + " ");
            System.out.println(log_list.get(i+2) + " ");
        }

        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.print("이전 화면으로 돌아가시려면 'B', 'b'를 입력해주세요 : ");
    }


    public static void LogCheck() throws IOException, InterruptedException {
        while(true) {
            Print_Log();
            Scanner s = new Scanner(System.in);
            char confirm;
            confirm = s.next().charAt(0);
            if(confirm == 'B' || confirm == 'b') {
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

