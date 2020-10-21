package com.konkuk.controller;

import com.konkuk.Main;
import com.konkuk.UI;
import com.konkuk.asset.Langs;
import com.konkuk.dto.DayOff;
import com.konkuk.service.DayOffHistoryService;

public class DayOffHistoryController extends Controller{
    public enum Menu{
        ANNUAL_HISTORY("1"),
        ADVANCED_SEARCH("2");

        private String name;

        Menu(String menuName){
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
            anuualHistory();
        } else if(menu.equals(Menu.ADVANCED_SEARCH.getMenu())){
            adnvancedSearch();
        }
        // back 메뉴 처리


        return new MainController();
    }
    public void anuualHistory(){
        //연간 연차내역 조회
    }
    public void adnvancedSearch(){
        //연차내역 상세 검색
    }
}
