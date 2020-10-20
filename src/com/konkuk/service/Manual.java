package com.konkuk.service;
import java.util.*;

public class Manual {
    private int mnum;  //메뉴 번호
    private int mnum2;  //기능 메뉴 번호
    private String reason;  //사유
    private String start_date;  //시작 시간
    private String end_date;  //종료 시간
    private int lnum;  //연차 번호(list 검색용)
    private float count;  //추가 혹은 차감할 연차개수
    private float fcount; //추가 혹은 차감된 연차개수
    private int day = 8;  //연차 시간
    private int halfday = 4;  //반차 시간
    private float dcount = 1.0f;  //연차 개수
    private float hdcount = 0.5f;  //반차 개수
    private int list_num = 1;  //연차 번호(db추가용)

    Scanner s = new Scanner(System.in);

    public void menu(){

        while (true) {
            System.out.println("------------------------------------------");
            System.out.println("1. 연차 사용");
            System.out.println("2. 연차 추가");
            System.out.println("3. 연차 수정 및 취소");
            System.out.println("4. 연차 차감");
            System.out.println("------------------------------------------");
            System.out.print("입력: ");
            mnum = s.nextInt();

            if(mnum==1) {
                use();
                break;
            } else if(mnum==2) {
                add();
                break;
            } else if(mnum==3) {
                change_cancel();
                break;
            } else if(mnum==4) {
                reduct();
                break;
            } else {
                System.out.println("다시 입력하세요.");
                continue;
            }

        }

    }

    private void use(){
        System.out.println("------------------------------------------");
        System.out.println("1. 연차");
        System.out.println("2. 반차");
        System.out.println("------------------------------------------");
        System.out.print("입력: ");
        mnum2 = s.nextInt();

        System.out.printf("연차 사용 사유를 입력하세요: ");
        reason = s.nextLine();
        System.out.printf("연차 사용 시작 시간을 입력하세요: ");
        start_date = s.nextLine();

        if(mnum2==1){       //연차 사용
            //시간 입력 확인


            //end_date 계산


            //fcount 계산 (잔여 연차수)
            //fcount = Employee.getCount() - 1;         //getCount()는 임시함수. 사용자의 원래연차수 가져오는 함수
        } else if(mnum2==2){        //반차 사용
            //시간 입력 확인


            //end_date 계산


            //fcount 계산 (잔여 연차수)
            //fcount = Employee.getCount() - 0.5;
        }


        //z 객체에 사용 내역 저장
        //Z z = new Z(list_num,name,reason,start_date,end_date,fcount);


        //사용list db?에 저장


        list_num += 1;
    }

    private void add(){
        System.out.printf("연차 추가 사유를 입력하세요: ");
        reason = s.nextLine();
        System.out.printf("연차 추가 개수를 입력하세요: ");
        count = s.nextFloat();

        //fcount = Employee.getCount() + count;  //employee 연차개수
        //db저장

    }

    private void change_cancel(){
        System.out.println("------------------------------------------");
        System.out.println("1. 연차 수정");
        System.out.println("2. 연차 취소");
        System.out.println("------------------------------------------");
        System.out.print("입력: ");
        mnum2 = s.nextInt();

        //연차 사용 리스트 출력

        if(mnum2==1){    //연차 수정
            System.out.printf("연차 번호를 입력하세요: ");
            lnum = s.nextInt();
            System.out.printf("연차 사유를 입력하세요(수정하지 않을 시 'p' 나 'P'를 입력하세요): ");
            reason = s.nextLine();
            System.out.printf("연차 시작 시간을 입력하세요(수정하지 않을 시 'p' 나 'P'를 입력하세요): ");
            start_date = s.nextLine();

            //연차 수정 반영


        } else if(mnum2==2){     //연차 취소
            System.out.printf("연차 번호를 입력하세요: ");
            lnum = s.nextInt();
            //연차 사용정보 리스트에서 삭제하기

            System.out.println("연차 사용을 취소합니다.");
        }
    }

    private void reduct(){
        System.out.printf("연차 차감 사유를 입력하세요: ");
        reason = s.nextLine();
        System.out.printf("연차 차감 개수를 입력하세요: ");
        count = s.nextFloat();

        //fcount = Employee.getCount() - count;  //Employee 연차개수
        //db저장

    }
}
