package com.konkuk.controller;

import com.konkuk.Main;
import com.konkuk.UI;
import com.konkuk.Utils;
import com.konkuk.asset.Langs;
import com.konkuk.dto.DayOff;
import com.konkuk.service.DayOffHistoryService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class DayOffHistoryController extends Controller{
    public int annualPage;
    public int searchPage;
    public int employeeId;
    public DayOffHistoryController(int employeeId){
        this.annualPage = 0;
        this.searchPage = 0;
        this.employeeId = employeeId;
    }


    public enum Menu{
        ANNUAL_HISTORY("1"),
        ADVANCED_SEARCH("2"),
        BACK_PAGE1("b"),
        BACK_PAGE2("B");
        private String name;

        Menu(String menuName){
            this.name = menuName;
        }
        String getMenu(){
            return name;
        }
    }

    public enum PageMenu{
        NEXT_PAGE("1"),
        PREV_PAGE("2"),
        BACK_PAGE1("b"),
        BACK_PAGE2("B");
        private String name;

        PageMenu(String menuName){
            this.name = menuName;
        }
        String getMenu(){
            return name;
        }
    }
    public enum Option{
        ANNUAL("1"),
        RANGE("2");
        private String name;

        Option(String menuName){
            this.name = menuName;
        }
        String getMenu(){
            return name;
        }
    }
    public Controller start(){

        UI.print(Langs.DAY_OFF_HISTORY_MAIN);
        String menu = UI.getInput();
        if(menu.equals(Menu.ANNUAL_HISTORY.getMenu())){
            history(Option.ANNUAL.getMenu());
        } else if(menu.equals(Menu.ADVANCED_SEARCH.getMenu())){
            history(Option.RANGE.getMenu());
        } else if(menu.equals(Menu.BACK_PAGE1.getMenu())||menu.equals(Menu.BACK_PAGE2.getMenu())){
            return new MainController();
        }


        return new MainController();
    }
    private Controller pageMenu(String options, DayOffHistoryService history){
        UI.print(Langs.DAY_OFF_HISTORY_PAGE);
        String menu = UI.getInput();

        if(menu.equals(PageMenu.NEXT_PAGE.getMenu())){
            if(options.equals(Option.ANNUAL.getMenu())) {
                if (history.isNextPageExists(this.annualPage))
                    this.annualPage++;
                else {
                    UI.print(Langs.DAY_OFF_LAST_PAGE);
                    this.history(options);
                }
            }else if(options.equals(Option.RANGE.getMenu())){
                if (history.isNextPageExists(this.searchPage))
                    this.searchPage++;
                else {
                    UI.print(Langs.DAY_OFF_LAST_PAGE);
                    this.history(options);
                }
            }
        }else if(menu.equals(PageMenu.PREV_PAGE.getMenu())){
            if(options.equals(Option.ANNUAL.getMenu())){
                if(history.isPrevPageExists(this.annualPage))
                    this.annualPage--;
                else{
                    UI.print(Langs.DAY_OFF_FIRST_PAGE);
                    this.history(options);
                }
            }else if(options.equals(Option.RANGE.getMenu())){
                if (history.isNextPageExists(this.searchPage))
                    this.searchPage--;
                else {
                    UI.print(Langs.DAY_OFF_LAST_PAGE);
                    this.history(options);
                }
            }

        }else if(menu.equals(PageMenu.BACK_PAGE1.getMenu())||menu.equals(PageMenu.BACK_PAGE2.getMenu())){
            return new DayOffHistoryController(this.employeeId);
        }
        return new DayOffHistoryController(this.employeeId);
    }
    private Controller history(String options) {
        boolean result = false;

        String checkString;
        String checkString1;

        Date startDate = new Date();
        Date endDate = new Date();

        DayOffHistoryService history = new DayOffHistoryService();

        if(options.equals(Option.ANNUAL.getMenu())){
            result = history.getHistory(this.employeeId, this.annualPage, startDate, endDate); // 기능 미완성
        }else if(options.equals(Option.RANGE.getMenu())){
            while(true){
                UI.print(Langs.DAY_OFF_HISTORY_DATE_START);
                checkString = UI.getInput();
                startDate = Utils.stringToDate(checkString);

                UI.print(Langs.DAY_OFF_HISTORY_DATE_END);
                checkString1 = UI.getInput();
                endDate = Utils.stringToDate(checkString1);

                if(Utils.isValidationDate(checkString)||Utils.isValidationDate(checkString1)){
                    UI.print("존재하지 않는 날짜입니다. 올바른 날짜를 입력해주세요."); // Langs에 추가
                    continue;
                }

                if(checkString.length()!=8 || checkString1.length()!=8){
                    UI.print("글자 수를 다시 확인해주세요.");// Langs에 추가
                    continue;
                }

                if(startDate == null || endDate == null) {
                    UI.print("숫자로만 이루어진 8자를 입력해주세요.");// Langs에 추가
                    continue;
                }
                break;
            }

            result = history.getHistory(this.employeeId,this.searchPage, startDate, endDate);
        }

        if(!result){
            UI.print(Langs.DAY_OFF_DSNT_EXIST);
            return new DayOffHistoryController(this.employeeId);
        }
        this.pageMenu(options, history);
        return new DayOffHistoryController(this.employeeId);

    }
}
