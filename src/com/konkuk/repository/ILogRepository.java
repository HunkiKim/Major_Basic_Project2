package com.konkuk.repository;

import com.konkuk.dto.Employee;

import java.io.IOException;
import java.util.List;

public interface ILogRepository {
    public Employee add(Employee employee) throws IOException;
    public List<Employee> findByName(String name);
    public List<Employee> findBySalary(int salary);
    public List<Employee> findById(int id);
    public Employee findByExactId(int id);
    public Employee update(int targetId, Employee data) throws IOException;
    public void delete(int targetId) throws IOException;
}