package com.konkuk.repository;

import com.konkuk.service.Utils;
import com.konkuk.asset.Langs;
import com.konkuk.asset.Settings;
import com.konkuk.dto.Employee;
import java.util.List;

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
            createEmptyDataFile(Settings.DATA_EMPLOYEE, Employee.getHeader());
        }
    }

    private static class Instance {
        private static final EmployeeRepository instance = new EmployeeRepository();
    }

    public static EmployeeRepository getInstance() {
        return Instance.instance;
    }

    private int maxId = -1;
    public void add(Employee employee) {
        if(maxId == -1) {
            employeeList.forEach((e -> maxId = Math.max(maxId, e.id)));
        }
        employee.id = ++maxId;
        // employee save
    }
}
