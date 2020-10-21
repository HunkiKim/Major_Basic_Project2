package com.konkuk.repository;

import com.konkuk.Utils;
import com.konkuk.asset.Langs;
import com.konkuk.asset.Settings;
import com.konkuk.dto.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository extends Repository {

    private List<Employee> employeeList;

    private EmployeeRepository() {
        this.debugTitle = "Employee";
        if (isDataFileExists(Settings.DATA_EMPLOYEE)) {
            employeeList = loadData(Settings.DATA_EMPLOYEE, (parsedData, uniquePolicy) -> {
                int id = Integer.parseInt(parsedData.get(0));
                String name = parsedData.get(1);
                int salary = Integer.parseInt(parsedData.get(2));
                int residualDayOff = Integer.parseInt(parsedData.get(3));
                if (uniquePolicy.contains(id)) {
                    Utils.exit(Langs.VIOLATE_UNIQUE_KEY);
                }
                return new Employee(id, name, salary, residualDayOff);
            });
        } else {
            createEmptyDataFile(Settings.DATA_EMPLOYEE, Employee.getHeader());
        }
    }

    private static class Instance {
        private static final EmployeeRepository instance = new EmployeeRepository();
    }

    public static EmployeeRepository getInstance() {
        return Instance.instance;
    }

    // todo: 단기 - 껍데기만 만들어둠
    private int maxId = -1;
    public Employee add(Employee employee) {
        if (maxId == -1) {
            employeeList.forEach((e -> maxId = Math.max(maxId, e.id)));
        }
        employee.id = ++maxId;
        // employee save
        return employee;
    }

    // todo: 단기 - 껍데기만 만들어둠, 부분 문자열 검색
    public List<Employee> findByName(String name) {
        List<Employee> results = new ArrayList<>();
        results.add(new Employee(0, "임시", 1000, 5));
        return results;
    }

    // todo: 단기 - 껍데기만 만들어둠색, 완전 일치 검색
    public List<Employee> findBySalary(int salary) {
        List<Employee> results = new ArrayList<>();
        results.add(new Employee(0, "임시", 1000, 5));
        return results;
    }

    // todo: 단기 - 껍데기만 만들어둠, 부분 문자열 검색
    public List<Employee> findById(int id) {
        List<Employee> results = new ArrayList<>();
        results.add(new Employee(0, "임시", 1000, 5));
        return results;
    }

    // todo: 단기 - 껍데기만 만들어둠, 완전 일치 검색
    public Employee findByExactId(int id) {
        return new Employee(0, "임시", 1000, 5);
    }

    // todo: 단기 - 껍데기만 만들어둠
    public Employee update(int targetId, Employee datas) {
        return datas;
    }

    // todo: 단기 - 껍데기만 만들어둠
    public Boolean delete(int targetId) {
        return true;
    }
}
