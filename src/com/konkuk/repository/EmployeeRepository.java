package com.konkuk.repository;

import com.konkuk.Utils;
import com.konkuk.asset.Langs;
import com.konkuk.asset.Settings;
import com.konkuk.dto.Employee;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class EmployeeRepository extends Repository {

    private List<Employee> employeeList;

    private EmployeeRepository() {
        this.debugTitle = "Employee";
        if(isDataFileExists(Settings.DATA_EMPLOYEE)) {
            employeeList = loadData(Settings.DATA_EMPLOYEE, (parsedData, uniquePolicy) -> {
                int id = Integer.parseInt(parsedData.get(0));
                String name = parsedData.get(1);
                int salary = Integer.parseInt(parsedData.get(2));
                int residualDayOff = Integer.parseInt(parsedData.get(3));
                if(uniquePolicy.contains(id)) {
                    Utils.exit(Langs.VIOLATE_UNIQUE_KEY);
                }
                return new Employee(id, name, salary, residualDayOff);
            });
        } else {
            try {
                createEmptyDataFile(Settings.DATA_EMPLOYEE, Employee.getHeader());
            } catch (IOException e) {
                // 다음파일 해보고 수정
                Utils.exit("Employee Repository IOE");
            }
        }
    }

    private static class Instance {
        private static final EmployeeRepository instance = new EmployeeRepository();
    }

    public static EmployeeRepository getInstance() {
        return Instance.instance;
    }

    public void add(Employee employee) {
        // save to database
    }
}
