package com.konkuk.repository;

import com.konkuk.Utils;
import com.konkuk.asset.Langs;
import com.konkuk.asset.Settings;
import com.konkuk.dto.Employee;

import java.io.IOException;
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
                float residualDayOff = Float.parseFloat(parsedData.get(3));
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

    private List<String> parseDtoToList(Employee employee) {
        List<String> result = new ArrayList<>();
        result.add(String.valueOf(employee.id));
        result.add(employee.name);
        result.add(String.valueOf(employee.salary));
        result.add(String.valueOf(employee.residualDayOff));
        return result;
    }

    private int maxId = -1;
    @Override
    public Employee add(Employee employee) throws IOException {
        if (maxId == -1) {
            employeeList.forEach((e -> maxId = Math.max(maxId, e.id)));
        }
        employee.id = ++maxId;
        addDataLine(parseDtoToList(employee));
        employeeList.add(employee);
        return employee;
    }

    @Override
    public List<Employee> findByName(String name) {
        List<Employee> results = new ArrayList<>();
        employeeList.forEach((employee -> {
            if(employee.name.contains(name)) results.add(employee);
        }));
        return results;
    }

    @Override
    public List<Employee> findBySalary(int salary) {
        List<Employee> results = new ArrayList<>();
        employeeList.forEach((employee -> {
            if(employee.salary == salary) results.add(employee);
        }));
        return results;
    }

    @Override
    public List<Employee> findById(int id) {
        List<Employee> results = new ArrayList<>();
        employeeList.forEach((employee -> {
            if(String.valueOf(employee.id).contains(String.valueOf(id))) results.add(employee);
        }));
        return results;
    }

    @Override
    public Employee findByExactId(int id) {
        Employee result = null;
        for (Employee e : employeeList) {
            if (e.id == id) {
                result = e;
                break;
            }
        }
        return result;
    }

    @Override
    public Employee update(int targetId, Employee employee) throws IOException {
        deleteDataLine(targetId);
        employee.id = targetId;
        addDataLine(parseDtoToList(employee));
        return employee;
    }

    @Override
    public void delete(int targetId) throws IOException {
        deleteDataLine(targetId);
    }
}
