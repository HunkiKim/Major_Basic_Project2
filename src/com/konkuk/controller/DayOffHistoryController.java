package com.konkuk.controller;

import com.konkuk.Main;
import com.konkuk.UI;
import com.konkuk.asset.Langs;
import com.konkuk.dto.DayOff;
import com.konkuk.service.DayOffHistoryService;

public class DayOffHistoryController extends Controller{
    public int annualPage;
    public int searchPage;
    public DayOffHistoryController(){
        this.annualPage = 0;
        this.searchPage = 0;
    }
    public enum Menu{
        ANNUAL_HISTORY("1"),
        ADVANCED_SEARCH("2");
        //"b", "B" 추가
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
        PREV_PAGE("2");
        //"b", "B" 추가
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
        }
        // back 메뉴 처리


        return new MainController();
    }
    public Controller annualHistory(){
        //연간 연차내역 조회
        boolean result;

        DayOffHistoryService history = new DayOffHistoryService();
        result = history.annualHistory(this.annualPage);
        if(!result){
            UI.print("해당 연차내역이 존재하지 않습니다.\n"); //Langs에 추가
            return new DayOffHistoryController();
        }

        UI.print(Langs.DAY_OFF_HISTORY_PAGE);
        String menu = UI.getInput();

        if(menu.equals(PageMenu.NEXT_PAGE.getMenu())){
            // back -> DayOffHistoryController
            if(history.isExistsNextPage(this.annualPage)) this.annualPage++;
            else{
                UI.print("마지막 페이지입니다.");//Langs에 추가
                this.annualHistory();
            }
        }else if(menu.equals(PageMenu.PREV_PAGE.getMenu())){
            // back -> DayOffHistoryController
            if(history.isExistsPrevPage(this.annualPage)) this.annualPage--;
            else{
                UI.print("첫 페이지입니다.");// Langs에 추가
                this.annualHistory();
            }
        }
        return new DayOffHistoryController();
    }
    public Controller advancedSearch(){
        //연차내역 상세 검색
        boolean result;

        DayOffHistoryService history = new DayOffHistoryService();
        result = history.advancedSearch(this.searchPage);
        if(!result){
            UI.print("해당 연차내역이 존재하지 않습니다.\n"); // Langs에 추가
            return new DayOffHistoryController();
        }
        return new DayOffHistoryController();
    }
}
