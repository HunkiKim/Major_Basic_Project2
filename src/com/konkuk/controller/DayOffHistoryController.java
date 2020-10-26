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

        UI.print2(Langs.DAY_OFF_HISTORY_MAIN);
        String menu = UI.getInput();

        if(menu.equals(Menu.ANNUAL_HISTORY.getMenu())){
            history(Option.ANNUAL.getMenu());
        } else if(menu.equals(Menu.ADVANCED_SEARCH.getMenu())){
            history(Option.RANGE.getMenu());
        } else if(menu.equals(Menu.BACK_PAGE1.getMenu())||menu.equals(Menu.BACK_PAGE2.getMenu())){
            return new MainController();
        }else{
            UI.print(Langs.MENU_ERROR);
            this.start();
        }


        return new MainController();
    }
    private Controller pageMenu(String options, DayOffHistoryService history){
        UI.print2(Langs.DAY_OFF_HISTORY_PAGE);
        String menu = UI.getInput();

        if(menu.equals(PageMenu.NEXT_PAGE.getMenu())){
            if(options.equals(Option.ANNUAL.getMenu())) {
                if (history.isNextPageExists(this.annualPage)) {
                    //UI.print("다음 페이지 존재");
                    this.annualPage++;

                }
                else {
                    UI.print(Langs.DAY_OFF_LAST_PAGE);

                }
                this.history(options);
            }else if(options.equals(Option.RANGE.getMenu())){
                if (history.isNextPageExists(this.searchPage)) {
                    //UI.print("다음 페이지 존재");
                    this.searchPage++;

                }
                else {
                    UI.print(Langs.DAY_OFF_LAST_PAGE);

                }
                this.pageMenu(options, history);
            }

        }else if(menu.equals(PageMenu.PREV_PAGE.getMenu())){
            if(options.equals(Option.ANNUAL.getMenu())){
                if(history.isPrevPageExists(this.annualPage)) {
                    //UI.print("이전 페이지 존재");
                    this.annualPage--;

                }
                else{
                    UI.print(Langs.DAY_OFF_FIRST_PAGE);

                }
                this.history(options);
            }else if(options.equals(Option.RANGE.getMenu())){
                if (history.isNextPageExists(this.searchPage)) {
                    //UI.print("이전 페이지 존재");
                    this.searchPage--;

                }
                else {
                    UI.print(Langs.DAY_OFF_LAST_PAGE);
                }
                this.pageMenu(options, history);
            }

        }else if(menu.equals(PageMenu.BACK_PAGE1.getMenu())||menu.equals(PageMenu.BACK_PAGE2.getMenu())){
            return this.start();
        }
        else{
            UI.print(Langs.INPUT_ERROR);
            this.pageMenu(options, history);
        }
        return this.start();
    }
    private Controller history(String options) {
        boolean result = false;

        String checkString;
        String checkString1;

        Date startDate = new Date();
        Date endDate = new Date();

        DayOffHistoryService history = new DayOffHistoryService();

        if(options.equals(Option.ANNUAL.getMenu())){

            result = history.getHistory(this.employeeId, this.annualPage, startDate, endDate, 1); // 기능 미완성
        }else if(options.equals(Option.RANGE.getMenu())){
            while(true){
                UI.print2(Langs.DAY_OFF_HISTORY_DATE_START);
                checkString = UI.getInput();
                if(checkString.equals("b")||checkString.equals("B")){
                    return this.start();
                }
                UI.print2(Langs.DAY_OFF_HISTORY_DATE_END);
                checkString1 = UI.getInput();
                if(checkString1.equals("b")||checkString1.equals("B")){
                    return this.start();
                }

                if(!Utils.isOnlyNumber(checkString) || !Utils.isOnlyNumber(checkString1)) {
                    UI.print(Langs.DAY_OFF_LETTER_ERROR);
                    continue;
                }
                if(checkString.length()!=8 || checkString1.length()!=8){
                    UI.print(Langs.DAY_OFF_LENGTH_ERROR);
                    continue;
                }
                if(!Utils.isValidationDate(checkString)||!Utils.isValidationDate(checkString1)){
                    UI.print(Langs.DAY_OFF_INVALIDATION_DATE);
                    continue;
                }

                startDate = Utils.stringToDate(checkString+" 00:00");
                endDate = Utils.stringToDate(checkString1+" 00:00");
                break;
            }

            result = history.getHistory(this.employeeId,this.searchPage, startDate, endDate, 2);
        }

        if(!result){
            UI.print(Langs.DAY_OFF_DSNT_EXIST);
            return this.start();
        }
        this.pageMenu(options, history);
        return this.start();

    }
}
