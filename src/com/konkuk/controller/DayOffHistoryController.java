package com.konkuk.controller;

import com.konkuk.Main;
import com.konkuk.UI;
import com.konkuk.asset.Langs;
import com.konkuk.dto.DayOff;
import com.konkuk.service.DayOffHistoryService;

import java.util.Date;
public class DayOffHistoryController extends Controller{
    public int annualPage;
    public int searchPage;
    public DayOffHistoryController(){
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

    public Controller start(){

        UI.print(Langs.DAY_OFF_HISTORY_MAIN);
        String menu = UI.getInput();
        if(menu.equals(Menu.ANNUAL_HISTORY.getMenu())){
            annualHistory();
        } else if(menu.equals(Menu.ADVANCED_SEARCH.getMenu())){
            advancedSearch();
        } else if(menu.equals(Menu.BACK_PAGE1.getMenu())||menu.equals(Menu.BACK_PAGE2.getMenu())){
            return new MainController();
        }


        return new MainController();
    }
    public Controller annualHistory(){
        //연간 연차내역 조회
        boolean result;

        DayOffHistoryService history = new DayOffHistoryService();
        result = history.annualHistory(this.annualPage); // 기능 미완성
        if(!result){
            UI.print(Langs.DAY_OFF_DSNT_EXIST);
            return new DayOffHistoryController();
        }

        UI.print(Langs.DAY_OFF_HISTORY_PAGE);
        String menu = UI.getInput();

        if(menu.equals(PageMenu.NEXT_PAGE.getMenu())){
            if(history.isExistsNextPage(this.annualPage)) this.annualPage++;
            else{
                UI.print(Langs.DAY_OFF_LAST_PAGE);//Langs에 추가
                this.annualHistory();
            }
        }else if(menu.equals(PageMenu.PREV_PAGE.getMenu())){
            if(history.isExistsPrevPage(this.annualPage)) this.annualPage--;
            else{
                UI.print(Langs.DAY_OFF_FIRST_PAGE);// Langs에 추가
                this.annualHistory();
            }
        }else if(menu.equals(PageMenu.BACK_PAGE1.getMenu())||menu.equals(PageMenu.BACK_PAGE2.getMenu())){
            return new DayOffHistoryController();
        }
        return new DayOffHistoryController();
    }
    public Controller advancedSearch(){
        //연차내역 상세 검색
        boolean result;
        Date startDate = new Date();
        Date endDate = new Date();



        DayOffHistoryService history = new DayOffHistoryService();
        result = history.advancedSearch(this.searchPage, startDate, endDate); // 기능 미완성
        if(!result){
            UI.print(Langs.DAY_OFF_DSNT_EXIST);
            return new DayOffHistoryController();
        }

        return new DayOffHistoryController();
    }
}
