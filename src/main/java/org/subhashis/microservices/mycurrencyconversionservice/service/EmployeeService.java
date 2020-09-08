package org.subhashis.microservices.mycurrencyconversionservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.subhashis.microservices.mycurrencyconversionservice.model.Employee;
import org.subhashis.microservices.mycurrencyconversionservice.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findById(String id) {
        return employeeRepository.findById(id);
    }

    public Employee add(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteAllInBatch() {
        employeeRepository.deleteAllInBatch();
    }
}
