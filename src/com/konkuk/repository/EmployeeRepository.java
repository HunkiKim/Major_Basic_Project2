package com.konkuk.repository;

import com.konkuk.Utils;
import com.konkuk.asset.Langs;
import com.konkuk.asset.Settings;
import com.konkuk.dto.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository extends Repository implements  IEmployeeRepository {

    private List<Employee> employeeList;

    private EmployeeRepository(String dataFilePath) {
        super(dataFilePath);
        this.debugTitle = "Employee";
        if (isDataFileExists()) {
            employeeList = loadData((parsedData, uniquePolicy) -> {
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
            createEmptyDataFile(Employee.getHeader());
        }
    }

    private static class Instance {
        private static final EmployeeRepository instance = new EmployeeRepository(Settings.DATA_EMPLOYEE);
    }

    public static EmployeeRepository getInstance() {
        return Instance.instance;
    }

    // todo: 단기 - 껍데기만 만들어둠
    private int maxId = -1;
    @Override
    public Employee add(Employee employee) {
        if (maxId == -1) {
            employeeList.forEach((e -> maxId = Math.max(maxId, e.id)));
        }
        employee.id = ++maxId;
        // employee save
        return employee;
    }

    @Override
    public List<Employee> findByName(String name) {
        List<Employee> results = new ArrayList<>();
        results.add(new Employee(0, "임시", 1000, 5));
        return results;
    }

    @Override
    public List<Employee> findBySalary(int salary) {
        List<Employee> results = new ArrayList<>();
        results.add(new Employee(0, "임시", 1000, 5));
        return results;
    }

    @Override
    public List<Employee> findById(int id) {
        List<Employee> results = new ArrayList<>();
        results.add(new Employee(0, "임시", 1000, 5));
        return results;
    }

    @Override
    public Employee findByExactId(int id) {
        return new Employee(0, "임시", 1000, 5);
    }

    @Override
    public Employee update(int targetId, Employee datas) {
        return datas;
    }

    @Override
    public Boolean delete(int targetId) {
        return true;
    }
}
