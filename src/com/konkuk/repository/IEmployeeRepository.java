package com.konkuk.repository;

import com.konkuk.dto.Employee;

import java.io.IOException;
import java.util.List;

public interface IEmployeeRepository {
    Employee add(Employee employee) throws IOException;
    List<Employee> findByName(String name);
    List<Employee> findBySalary(int salary);
    List<Employee> findBySalaryBetween(int salary, int salary2);
    List<Employee> findById(int id);
    Employee findByExactId(int id);
    void update(int targetId, Employee data);
    void delete(int targetId);
}
