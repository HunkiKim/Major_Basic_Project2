package com.konkuk.repository;

import com.konkuk.dto.Employee;

import java.util.List;

public interface IEmployeeRepository {
    public Employee add(Employee employee);
    public List<Employee> findByName(String name);
    public List<Employee> findBySalary(int salary);
    public List<Employee> findById(int id);
    public Employee findByExactId(int id);
    public Employee update(int targetId, Employee datas);
    public Boolean delete(int targetId);
}
