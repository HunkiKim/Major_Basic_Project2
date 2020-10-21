package com.konkuk.controller;

import com.konkuk.controller.EmployeeController.Menu;
import com.konkuk.repository.Repository;
import com.konkuk.service.UI;

import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class LogController extends Controller {
    public enum Menu {LOG}
    protected Menu currentMenu;

    public LogController(Menu menu) {
        this.currentMenu = menu;
    }
    // EmployeeController와 그냥 동일하게 만들었음. MainController에서 case 3 : next = new LogController(LogController.Menu.LOG); 필요
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

    public static void Print_Log() {
        Repository rp = new Repository();
        try {
            File file = new File("log.txt");
            FileReader filereader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(filereader);
            String line = "";
            System.out.println("[로그내역]");
            System.out.println("-----------------------------------------------------------------------------------------");
            while((line = bufReader.readLine()) != null) {
                List<String> parsed = rp.parseDataLine(line); //Repository에 parseDataLine을 protected -> public 수정 필요
                String s = parsed.get(3);
                String[] arr;
                arr = s.split("");
                for (int i = 0; i < arr.length; i++) {
                    System.out.print(arr[i]);
                    if (i == 3 || i == 5)
                    {
                        System.out.print("/");
                    }

                }
                System.out.print(" [" + parsed.get(1) + "] ");
                System.out.println(parsed.get(2));
            }
            bufReader.close();
            System.out.println("-----------------------------------------------------------------------------------------");
            System.out.print("이전 화면으로 돌아가시려면 'B', 'b'를 입력해주세요 : ");
        }catch (FileNotFoundException e) {

        }catch (IOException e) {

        }
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

