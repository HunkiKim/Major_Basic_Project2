package com.konkuk.asset;

public final class Langs {
    private static final String HORIZON = "------------------------------\n";
    public static final String MAIN = HORIZON +
            "1. 직원 선택 및 관리\n" +
            "2. 직원 추가\n" +
            "3. 로그 조회\n" +
            "4. 프로그램 종료\n" +
            HORIZON;
    public static final String FAIL_TO_CREATE_DATA_FILE = "데이터 파일 생성에 실패했습니다.";
    public static final String VIOLATE_UNIQUE_KEY = "식별자 규칙에 위배되는 데이터 파일 발견으로 종료합니다.";
    public static final String DATA_FILE_HEADER_EMPLOYEE = "\"사번\",\"이름\",\"연봉\",\"잔여 연차\"";
}
