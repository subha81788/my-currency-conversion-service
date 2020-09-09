package org.subhashis.microservices.mycurrencyconversionservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.subhashis.microservices.mycurrencyconversionservice.model.Employee;
import org.subhashis.microservices.mycurrencyconversionservice.model.EmployeeIdentity;
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

    public Optional<List<Employee>> findById(String accountId, String individualId) {
        return employeeRepository.findByIdAccountIdAndIdIndividualId(accountId, individualId);
    }

    public Optional<List<Employee>> findByAccountId(String accountId) {
        return employeeRepository.findByIdAccountId(accountId);
    }

    public Employee add(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> addAll(List<Employee> employeeList) {
        return employeeRepository.saveAll(employeeList);
    }

    /*
    public int update(Employee employee) {
        return employeeRepository.update(employee.getName(),
                employee.getAddress().getStreet(),
                employee.getAddress().getCity(),
                employee.getCurrency().getSymbol(),
                employee.getCurrency().getFullName(),
                employee.getId().getAccountId(),
                employee.getId().getIndividualId());
    }
     */

    public void delete(EmployeeIdentity id) {
        employeeRepository.deleteByIdAccountIdAndIdIndividualId(id.getAccountId(), id.getIndividualId());
    }

    public void deleteAllInBatch() {
        employeeRepository.deleteAllInBatch();
    }
}
