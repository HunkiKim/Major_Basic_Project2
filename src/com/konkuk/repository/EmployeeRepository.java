package com.konkuk.repository;

import com.konkuk.UI;
import com.konkuk.asset.Settings;
import com.konkuk.dto.Employee;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    private List<Employee> employeeList;

    private EmployeeRepository() {
        if(isDataFileExists()) {
            employeeList = loadData();
        } else {
            try {
                createEmptyDataFile();
            } catch (IOException e) {
                // 다음파일 해보고 수정
                UI.print("Employee Repository IOE");
                System.exit(0);
            }
        }
    }

    private static class Instance {
        private static final EmployeeRepository instance = new EmployeeRepository();
    }

    public static EmployeeRepository getInstance() {
        return Instance.instance;
    }

    private boolean isDataFileExists() {
        File employee = new File(Settings.DATA_EMPLOYEE);
        return employee.exists();
    }

    private void createEmptyDataFile() throws IOException {
        UI.debug("데이터 파일 생성");
        File file = new File(Settings.DATA_EMPLOYEE);
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        // BOM
        bw.write(65279);
        bw.write(Employee.getHeader());
        bw.close();
    }

    private List<Employee> loadData() {
        UI.debug("데이터 파일 로드");
        List<Employee> result = new ArrayList<>();

        try {
            // 어차피 헤더 무시하긴 할껀데 BOM 처리가 필요할지.. 흠..
            List<String> lines = Files.readAllLines(Paths.get(Settings.DATA_EMPLOYEE));
            // todo: 일부 데이터가 없는경우 (사번은 무시, 기획서에서 사번만 고유하므로 나머지는 수정 필요를 명시하자)
            lines.forEach((line)-> {
                List<String> datas = new ArrayList<>();
                StringBuilder s = new StringBuilder();
                boolean error = false;
                boolean open = false;
                // 이전이 " 인지
                boolean escapeBefore = false;
                for(char c: line.toCharArray()) {
                    if(open) {
                        if(c == '"') {
                            if(escapeBefore) {
                                s.append(c);
                                escapeBefore = false;
                            } else {
                                escapeBefore = true;
                            }
                        } else if(c == ',') {
                            if(escapeBefore) {
                                datas.add(s.toString());
                                s = new StringBuilder();
                                open = false;
                            } else {
                                s.append(c);
                            }
                            escapeBefore = false;
                        } else {
                            // 이 경우에는 오류다. 이전이 " 인데 지금 " 나 , 외의 문
                            if(escapeBefore) {
                                error = true;
                                break;
                            } else {
                                s.append(c);
                                escapeBefore = false;
                            }
                        }
                    } else {
                        // 닫혀있는경우 , 나 " 가 아니면 이상한것
                        if(c == '"') {
                            open = true;
                        } else if(c == ',') {
                        } else {
                            // 첫줄 BOM의 경우 여기서 잡힌다.
                            error = true;
                            break;
                        }
                    }
                }
                if(!error) {
                    // 마지막 데이터 추가
                    datas.add(s.toString());

//                    datas.forEach((x)->System.out.println(x));
                    // todo: datas 2 dto
                }
            });
        } catch (IOException e) {

        }
        return result;
    }

    public void add(Employee employee) {
        // save to database
    }
}
