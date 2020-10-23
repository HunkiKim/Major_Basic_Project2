package com.konkuk.asset;

public final class Langs {
    private static final String HORIZON = "------------------------------\n";
    public static final String MAIN = HORIZON +
            "1. 직원 선택 및 관리\n" +
            "2. 직원 추가\n" +
            "3. 로그 조회\n" +
            "4. 프로그램 종료\n" +
            HORIZON;
    public static final String DAY_OFF_MAIN = HORIZON +
            "1. 연차 사용\n" +
            "2. 연차 추가\n" +
            "3. 연차 수정 및 취소\n" +
            "4. 연차 차감\n" +
            HORIZON +
            "입력: ";
    public static final String DAY_OFF_USE = HORIZON +
            "1. 연차\n" +
            "2. 반차\n" +
            HORIZON +
            "입력: ";
    public static final String DAY_OFF_CC = HORIZON +
            "1. 연차 수정\n" +
            "2. 연차 취소\n" +
            HORIZON +
            "입력: ";
    public static final String EMPLOYEE_OF_USE = HORIZON +
            "대상 입력:";


    public static final String EMPLOYEE_ID = "사번: ";
    public static final String EMPLOYEE_NAME = "이름: ";
    public static final String EMPLOYEE_SALARY = "연봉: ";
    public static final String EMPLOYEE_TARGET = "대상 입력: ";
    public static final String LENGTH_ERROR = "길이가 맞지 않습니다. 다시 입력해주세요.";
    public static final String LETTER_ERROR = "문법규칙에 맞지 않는 문자가 있습니다. 다시 입력해주세요.";
    public static final String DAY_OFF_REASON = "연차 사용 사유를 입력하세요: ";
    public static final String DAY_OFF_START = "연차 사용 시작 시간을 입력하세요: ";
    public static final String DAY_OFF_CHANGE_REASON = "연차 사유를 입력하세요(수정하지 않을 시 'p' 나 'P'를 입력하세요): ";
    public static final String DAY_OFF_CHANGE_START = "연차 시작 시간을 입력하세요(수정하지 않을 시 'p' 나 'P'를 입력하세요): ";
    public static final String DAY_OFF_ADD = "연차 추가 개수를 입력하세요: ";
    public static final String DAY_OFF_RED = "연차 차감 개수를 입력하세요: ";
    public static final String INPUT_NUM = "연차 번호를 입력하세요: ";
    public static final String INPUT_ERROR = "다시 입력하세요.";
    public static final String FAIL_TO_CREATE_DATA_FILE = "데이터 파일 생성에 실패했습니다.";
    public static final String VIOLATE_UNIQUE_KEY = "식별자 규칙에 위배되는 데이터 파일 발견으로 종료합니다.";
    public static final String DAY_OFF_DSNT_EXIST = "해당 연차내역이 존재하지 않습니다.";
    public static final String DAY_OFF_FIRST_PAGE = "첫 페이지입니다.";
    public static final String DAY_OFF_LAST_PAGE = "마지막 페이지입니다.";
    public static final String DATA_FILE_HEADER_EMPLOYEE = "\"사번\",\"이름\",\"연봉\",\"잔여 연차\"";
    public static final String DATA_FILE_HEADER_DAYOFF_USE = "\"사번\",\"이름\",\"사유\",\"연차 시작\",\"연차 종료\",\"잔여 연차 수\"";
    public static final String DATA_FILE_HEADER_DAYOFF = "\"번호\",\"사번\",\"변동 연차 수\",\"사유\",\"연차 시작\",\"연차 종료\",\"생성 날짜\"";


    public static final String DAY_OFF_HISTORY_MAIN = HORIZON +
            "1. 이번 연도 연차 조회\n" +
            "2. 상세 검색\n" +
            HORIZON +
            "입력 ";
    public static final String DAY_OFF_HISTORY_PAGE = "\n" +
            "1. 다음 연차 내역    " + "2. 이전 연차 내역\n"+
            HORIZON +
            "입력: ";
    public static final String DAY_OFF_INFO_PAGE = "\n" +
            "연차를 초과 사용하였습니다. 차감액을 조회하시겠습니까?\n"+
            "1.조회하기\n"+
            "2.메인화면 이동\n"+
            HORIZON +
            "입력: ";
    public static final String DAY_OFF_INFO_PAGE_REFUND = "\n" +
            "연차를 초과 사용하였습니다. 차감액을 조회하시겠습니까?\n"+
            HORIZON +
            "1.조회하기\n"+
            "2.메인화면 이동\n"+
            "입력: ";
    public static final String DAY_OFF_HISTORY_DATE_START = "\n연차 시작 날짜를 입력하세요: ";
    public static final String DAY_OFF_HISTORY_DATE_END = "\n연차 종료 날짜를 입력하세요: ";
}
