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
    private Date startDate;
    private Date endDate;

    public DayOffHistoryController(int employeeId){
        this.annualPage = 0;
        this.searchPage = 0;
        this.startDate = new Date();
        this.endDate = new Date();
        this.employeeId = employeeId;
    }
    public void clearPageNumber(){
        this.annualPage = 0;
        this.searchPage = 0;
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
        RANGE("2"),
        ALL("3");
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
        if(!Utils.menuCheck(menu)){
            Utils.pause(null);
            return this.start();
        }
        if(menu.equals(Menu.ANNUAL_HISTORY.getMenu())){
            this.clearPageNumber();
            return history(Option.ANNUAL.getMenu());
        } else if(menu.equals(Menu.ADVANCED_SEARCH.getMenu())){
            this.clearPageNumber();
            return history(Option.RANGE.getMenu());
        } else if(menu.equals(Menu.BACK_PAGE1.getMenu())||menu.equals(Menu.BACK_PAGE2.getMenu())){
            return new MainController();
        }


        return new MainController();
    }
    private Controller pageMenu(String options, DayOffHistoryService history){
        UI.print2(Langs.DAY_OFF_HISTORY_PAGE);
        String menu = UI.getInput();
        if(!Utils.menuCheck(menu)){
            Utils.pause(null);
            this.pageMenu(options, history);
        }

        if(menu.equals(PageMenu.NEXT_PAGE.getMenu())){
            if(options.equals(Option.ANNUAL.getMenu())) {
                if (history.isNextPageExists(this.annualPage)) {
                    //UI.print("?????? ????????? ??????");
                    this.annualPage++;

                }
                else {
                    Utils.pause(Langs.DAY_OFF_LAST_PAGE);
                }
                return this.history(options);
            }else if(options.equals(Option.RANGE.getMenu())){
                if (history.isNextPageExists(this.searchPage)) {
                    //UI.print("?????? ????????? ??????");
                    this.searchPage++;

                }
                else {
                    Utils.pause(Langs.DAY_OFF_LAST_PAGE);
                }
                history.getHistory(this.employeeId,this.searchPage, this.startDate, this.endDate, 2);
                return this.pageMenu(options, history);
            }else if(options.equals(Option.ALL.getMenu())){
                if (history.isNextPageExists(this.searchPage)) {
                    //UI.print("?????? ????????? ??????");
                    this.searchPage++;

                }
                else {
                    Utils.pause(Langs.DAY_OFF_LAST_PAGE);
                }
                history.getHistory(this.employeeId,this.searchPage, this.startDate, this.endDate, 3);

                return this.pageMenu(options, history);
            }

        }else if(menu.equals(PageMenu.PREV_PAGE.getMenu())){
            if(options.equals(Option.ANNUAL.getMenu())){
                if(history.isPrevPageExists(this.annualPage)) {
                    //UI.print("?????? ????????? ??????");
                    this.annualPage--;

                }
                else{
                    Utils.pause(Langs.DAY_OFF_FIRST_PAGE);
                }
                return this.history(options);
            }else if(options.equals(Option.RANGE.getMenu())){
                if (history.isPrevPageExists(this.searchPage)) {
                    //UI.print("?????? ????????? ??????");
                    this.searchPage--;

                }
                else {
                    Utils.pause(Langs.DAY_OFF_FIRST_PAGE);
                }
                history.getHistory(this.employeeId,this.searchPage, this.startDate, this.endDate, 2);
                return this.pageMenu(options, history);
            }else if(options.equals(Option.ALL.getMenu())){
                if (history.isPrevPageExists(this.searchPage)) {
                    //UI.print("?????? ????????? ??????");
                    this.searchPage--;

                }
                else {
                    Utils.pause(Langs.DAY_OFF_FIRST_PAGE);
                }
                history.getHistory(this.employeeId,this.searchPage, this.startDate, this.endDate, 3);
                return this.pageMenu(options, history);
            }


        }else if(menu.equals(PageMenu.BACK_PAGE1.getMenu())||menu.equals(PageMenu.BACK_PAGE2.getMenu())){
            return this.start();
        }
        else{
            Utils.pause(Langs.INPUT_ERROR);
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

            result = history.getHistory(this.employeeId, this.annualPage, startDate, endDate, 1);
        }else if(options.equals(Option.RANGE.getMenu())){
            while(true){
                UI.print2(Langs.DAY_OFF_HISTORY_DATE_START);
                checkString = UI.getInput();
                if(checkString.equals("b")||checkString.equals("B")){
                    return this.start();
                }
                if(checkString.equals("")){
                    return this.history("3");
                }
                UI.print2(Langs.DAY_OFF_HISTORY_DATE_END);
                checkString1 = UI.getInput();
                if(checkString1.equals("")){
                    return this.history("3");
                }
                if(checkString1.equals("b")||checkString1.equals("B")){
                    return this.start();
                }

                if(!Utils.isOnlyNumber(checkString) || !Utils.isOnlyNumber(checkString1)) {
                    Utils.pause(Langs.DAY_OFF_LETTER_ERROR);
                    continue;
                }
                if(checkString.length()!=8 || checkString1.length()!=8){
                    Utils.pause(Langs.DAY_OFF_LETTER_ERROR);
                    continue;
                }
                if(!Utils.isValidationDate(checkString)||!Utils.isValidationDate(checkString1)){
                    Utils.pause(Langs.DAY_OFF_INVALIDATION_DATE);
                    continue;
                }

                startDate = Utils.stringToDate(checkString+" 00:00");
                endDate = Utils.stringToDate(checkString1+" 00:00");
                break;
            }
            this.startDate = startDate;
            this.endDate = endDate;
            result = history.getHistory(this.employeeId,this.searchPage, startDate, endDate, 2);
        }
        else if(options.equals(Option.ALL.getMenu())){
            result = history.getHistory(this.employeeId, this.searchPage, startDate, endDate, 3);
        }

        if(!result){
            Utils.pause(Langs.DAY_OFF_DSNT_EXIST);
            return this.start();
        }
        return this.pageMenu(options, history);
        //return this.start();

    }
}
