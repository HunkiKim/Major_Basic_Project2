package com.konkuk.service;

import com.konkuk.UI;
import com.konkuk.asset.Langs;
import com.konkuk.dto.Employee;

import java.util.regex.Pattern;

public class EmployeeService {
    public boolean add(Employee employee) {
        // 비즈니스 로직


        return false;
    }
    public boolean idcheck(String id){
        for (int i = 0; i < id.length(); i++) { //숫자가 아닐시
            if (id.charAt(i) < 48 || id.charAt(i) > 58) {
                UI.print(Langs.LETTER_ERROR);
                return false;
            }
        }
        //길이 확인
        if(id.length()<1 || id.length()>7){
            UI.print(Langs.LENGTH_ERROR);
            return false;
        }
        return true;
    }

    public boolean namecheck(String name){
        if(!Pattern.matches("^[a-zA-Z가-힣]*$",name)){//정규표현식 한글
            UI.print(Langs.LETTER_ERROR);
            return false;
        }

        if(name.getBytes().length<1 || name.getBytes().length>32) { //한글 영어 둘다있기때문에 byte로 했습니다.
            UI.print(Langs.LETTER_ERROR);
            return false;
        }

        return true;
    }

    public boolean salarycheck(String salary){
        for (int i = 0; i < salary.length(); i++) { //숫자가 아닐시
            if (salary.charAt(i) < 48 || salary.charAt(i) > 58) {
                UI.print(Langs.LETTER_ERROR);
                return false;
            }
        }

        if(salary.length()<8 || salary.length()>10){
            UI.print(Langs.LENGTH_ERROR);
            return false;
        }

        return true;
    }

    public int check(String yn){
        if(yn.equals("Yes") || yn.equals("Y") || yn.equals("y") || yn.equals("yes")){
            return 0;
        }
        else if(yn.equals("No") || yn.equals("n") || yn.equals("N") || yn.equals("no")){
            return 1;
        }
        else{
            return -1;
        }
    }
}
