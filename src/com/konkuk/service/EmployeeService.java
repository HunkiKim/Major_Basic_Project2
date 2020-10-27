package com.konkuk.service;

import com.konkuk.UI;
import com.konkuk.Utils;
import com.konkuk.Utils.InputType;
import com.konkuk.asset.Langs;
import com.konkuk.dto.Employee;
import com.konkuk.exception.IllegalLengthException;
import com.konkuk.exception.IllegalLetterException;
import com.konkuk.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class EmployeeService {
    EmployeeRepository Erepositry = EmployeeRepository.getInstance();
    public boolean add(Employee employee) {
        // 비즈니스 로직


        return false;
    }

    public List<Employee> getEmployees(String input) throws IllegalLengthException, IllegalLetterException {
        int len = input.length();
        int byteLen = input.getBytes().length;
        InputType inputType = Utils.getInputType(input);

        if(inputType == InputType.NUMERIC) {
            int parsedInput = Integer.parseInt(input);
            if(len>=1 && len<=7) {
                return Erepositry.findById(parsedInput);
            } else if(len>=8 && len<=10){
                return Erepositry.findBySalary(parsedInput);
            } else {
                throw new IllegalLengthException();
            }
        } else if (inputType == InputType.LETTER) {
            if (byteLen<1 || byteLen>32) {
                throw new IllegalLengthException();
            } else {
                return Erepositry.findByName(input);
            }
        } else {
            // inputType == MIXED
            if(salarmeasure(input)) {
                int conversionSalary = salaryconversion(input);
                return Erepositry.findBySalaryBetween(conversionSalary, conversionSalary + 10000);
            } else {
                throw new IllegalLetterException();
            }
        }
    }

    public int salaryconversion(String target){ // 만으로 된 문자 빼고 0000 추가해주기
        String str = "";
        for(int i=0; i<target.length()-1; i++){
            str+=target.charAt(i);
        }
        str+="0000";
        return Integer.parseInt(str);
    }

    public boolean salarmeasure(String target){//1000만 같은식으로 나올떄 확인
        if(target.charAt(target.length()-1)=='만') {  // 끝이만일때
            for (int i = 0; i < target.length() - 1; i++) { //끝을제외한 나머지가 숫자인지
                if (target.charAt(i) < 48 || target.charAt(i) > 58) //숫자가 아닐경우
                    return false;
            }
            if (target.length() >= 4 && target.length() <= 6) { //숫자라면 길이가 올바른지 .
            } //올바르다면 마지막줄의 리턴1을 반환
        }else{
            return false; //끝이 만이 아닐경우
        }
        return true;
    }

    public int numletter(String target){ // 숫자 길이 확인
        if(target.charAt(0)>=48 && target.charAt(0)<=57){ //숫자인가 ?
            if(target.length()>=1 && target.length()<=7){ // 그렇다면 길이가 맞는가?
                return 1; // id
            }else if(target.length()>=8 && target.length()<=10){
                return 2; //salary
            }
            return -1; //길이안맞음
        }
        else{
            if(target.getBytes().length<1 || target.getBytes().length>32)
                return -3; // 문자의 길이가 안맞음
            return -2; //문자
        }

    }

    public boolean idcheck(String id){
        for (int i = 0; i < id.length(); i++) { //숫자가 아닐시
            if (id.charAt(i) < 48 || id.charAt(i) > 58) {
                UI.print(Langs.LETTER_ERROR);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return false;
            }
        }

        if(id.charAt(0)=='0' && id.length()!=1){
            UI.print(Langs.BLANK_SPACE_ERROR);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }
        //길이 확인
        if(id.length()<1 || id.length()>7){
            UI.print(Langs.LENGTH_ERROR);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }

    public boolean namecheck(String name){
        if(!Pattern.matches("^[a-zA-Z가-힣]*$",name)){//정규표현식 한글
            UI.print(Langs.LETTER_ERROR);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }

        if(name.getBytes().length<1 || name.getBytes().length>32) { //한글 영어 둘다있기때문에 byte로 했습니다.
            UI.print(Langs.LETTER_ERROR);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }

        return true;
    }

    public boolean salarycheck(String salary){
        for (int i = 0; i < salary.length(); i++) { //숫자가 아닐시
            if (salary.charAt(i) < 48 || salary.charAt(i) > 58) {
                UI.print(Langs.LETTER_ERROR);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return false;
            }
        }

        if(salary.length()<8 || salary.length()>10){
            UI.print(Langs.LENGTH_ERROR);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
