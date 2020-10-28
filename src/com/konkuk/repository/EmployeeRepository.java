package com.konkuk.repository;

import com.konkuk.Utils;
import com.konkuk.asset.Langs;
import com.konkuk.asset.Settings;
import com.konkuk.dto.Employee;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository extends Repository implements  IEmployeeRepository {

    private List<Employee> employeeList;
    String header = Employee.getHeader();

    private EmployeeRepository(String dataFilePath) {
        super(dataFilePath);
        this.debugTitle = "Employee";
        if (isDataFileExists()) {
            employeeList = loadData((parsedData) -> {
                int id = Integer.parseInt(parsedData.get(0));
                String name = parsedData.get(1);
                int salary = Integer.parseInt(parsedData.get(2));
                float residualDayOff = Float.parseFloat(parsedData.get(3));
                if (uniquePolicy.contains(id)) {
                    Utils.exit(this.debugTitle + " - " + Langs.VIOLATE_UNIQUE_KEY);
                }
                uniquePolicy.add(id);
                return new Employee(id, name, salary, residualDayOff);
            });
        } else {
            createEmptyDataFile(header);
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
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(0);
        result.add(df.format(employee.salary));
        result.add(String.valueOf(employee.residualDayOff));
        return result;
    }

    @Override
    public Employee add(Employee employee) throws IOException {
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
    public List<Employee> findBySalaryBetween(int salary, int salary2) {
        List<Employee> results = new ArrayList<>();
        employeeList.forEach((employee -> {
            if(employee.salary >= salary && employee.salary < salary2) results.add(employee);
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
    public void update(int targetId, Employee employee) {
        employee.id = targetId;
        for(int i = 0; i < employeeList.size(); i++) {
            if(employeeList.get(i).id == targetId) {
                employeeList.set(i, employee);
                break;
            }
        }
        reCreateFile();
    }

    @Override
    public void delete(int targetId) {
        for(int i = 0; i < employeeList.size(); i++) {
            if(employeeList.get(i).id == targetId) {
                employeeList.remove(i);
                break;
            }
        }
        reCreateFile();
    }

    private void reCreateFile() {
        createEmptyDataFile(this.header);
        employeeList.forEach(employee -> {
            try {
                addDataLine(parseDtoToList(employee));
            } catch (IOException ignored) {
            }
        });
    }
}
