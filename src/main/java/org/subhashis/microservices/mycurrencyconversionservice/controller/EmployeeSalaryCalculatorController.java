package org.subhashis.microservices.mycurrencyconversionservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.subhashis.microservices.mycurrencyconversionservice.model.Employee;
import org.subhashis.microservices.mycurrencyconversionservice.service.EmployeeService;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class EmployeeSalaryCalculatorController {

    @Value("${my-currency-exchange-service-uri}")
    private String myCurrencyExchangeServiceUri;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(path = "/employees")
    public List<Employee> getAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping(path = "/employeesById")
    public List<Employee> getEmployeesById(@RequestParam("accountId") String accountId, @RequestParam("individualId") String individualId) {
        if(StringUtils.isEmpty(accountId) || StringUtils.isEmpty(individualId)) {
            throw new IllegalArgumentException("AccountId or InvidualId is not specified");
        }
        return employeeService.findById(accountId,individualId).orElseThrow(() -> new EntityNotFoundException("Requested employee not found"));
    }


    @GetMapping(path = "/employeesByAccountId/{accountId}")
    public List<Employee> getEmployeesByAccountId(@PathVariable("accountId") String accountId) {
        if(StringUtils.isEmpty(accountId)) {
            throw new IllegalArgumentException("AccountId is not specified");
        }
        return employeeService.findByAccountId(accountId).orElseThrow(() -> new EntityNotFoundException("Requested employee account not found"));
    }

    @PostMapping(path = "/employees", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> createEmployee(@RequestBody @NotNull Employee employee) {
        return new ResponseEntity<>(employeeService.add(employee), HttpStatus.CREATED);
    }
}
