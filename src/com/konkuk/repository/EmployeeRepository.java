package com.konkuk.repository;

import com.konkuk.UI;
import com.konkuk.asset.Settings;
import com.konkuk.dto.Employee;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        File file = new File(Settings.DATA_EMPLOYEE);
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        // BOM
        bw.write(65279);
        bw.write(Employee.getHeader());
        bw.close();
    }

    private List<Employee> loadData() {
        List<Employee> result = new ArrayList<>();
        return result;
    }

    public void add(Employee employee) {
        // save to database
    }
}
