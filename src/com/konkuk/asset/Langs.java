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
    public static final String EMPLOYEE_OF_USE = HORIZON +
            "대상 입력:";

    public static final String EMPLOYEE_ID = "사번: ";
    public static final String EMPLOYEE_NAME = "이름: ";
    public static final String EMPLOYEE_SALARY = "연봉: ";
    public static final String DAY_OFF_REASON = "연차 사용 사유를 입력하세요: ";
    public static final String DAY_OFF_START = "연차 사용 시작 시간을 입력하세요: ";
    public static final String INPUT_ERROR = "다시 입력하세요.";
    public static final String LENGTH_ERROR = "길이가 맞지 않습니다. 다시 입력해주세요.";
    public static final String LETTER_ERROR = "문법규칙에 맞지 않는 문자가 있습니다. 다시 입력해주세요.";
    public static final String FAIL_TO_CREATE_DATA_FILE = "데이터 파일 생성에 실패했습니다.";
    public static final String VIOLATE_UNIQUE_KEY = "식별자 규칙에 위배되는 데이터 파일 발견으로 종료합니다.";
    public static final String DATA_FILE_HEADER_EMPLOYEE = "\"사번\",\"이름\",\"연봉\",\"잔여 연차\"";
    public static final String DATA_FILE_HEADER_DAYOFF = "\"번호\",\"사번\",\"변동 연차 수\",\"사유\",\"연차 시작\",\"연차 종료\",\"생성 날짜\"";
}
