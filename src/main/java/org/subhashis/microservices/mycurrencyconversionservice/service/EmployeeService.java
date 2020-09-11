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

    public Optional<Employee> findById(String accountId, String individualId) {
        //////////////////
        employeeRepository.getCurrencies(accountId, individualId).forEach(System.out::println);
        //////////////////
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

    public Employee update(Employee employee) {
        var emp = employeeRepository.findByIdAccountIdAndIdIndividualId(employee.getId().getAccountId(), employee.getId().getIndividualId()).get();
        emp.setName(employee.getName());
        emp.setAddress(employee.getAddress());
        emp.setCurrency(employee.getCurrency());
        return employeeRepository.save(emp);
        /*
        return employeeRepository.update(employee.getName(),
                employee.getAddress().getStreet(),
                employee.getAddress().getCity(),
                employee.getCurrency().getSymbol(),
                employee.getCurrency().getFullName(),
                employee.getId().getAccountId(),
                employee.getId().getIndividualId());
         */
    }

    public void delete(String accountId, String individualId) {
        employeeRepository.deleteByIdAccountIdAndIdIndividualId(accountId, individualId);
    }

    public void deleteAllInBatch() {
        employeeRepository.deleteAllInBatch();
    }
}
