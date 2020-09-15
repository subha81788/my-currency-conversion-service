package org.subhashis.microservices.mycurrencyconversionservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.subhashis.microservices.mycurrencyconversionservice.exception.EmployeeNotFoundException;
import org.subhashis.microservices.mycurrencyconversionservice.model.Employee;
import org.subhashis.microservices.mycurrencyconversionservice.service.EmployeeService;
import reactor.core.publisher.Flux;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RefreshScope
@RestController
public class EmployeeSalaryCalculatorController {

    @Value("${foo}")
    private String foo;
    @Value("${spring.datasource.h2.url}")
    private String dataSourceUrl;

    @Value("${my-currency-exchange-service-uri}")
    private String myCurrencyExchangeServiceUri;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/foo")
    public String getFoo() {
        return this.dataSourceUrl;
    }

    @GetMapping(path = "/employees")
    public List<Employee> getAllEmployees() {
        return this.employeeService.findAll();
    }

    @GetMapping(path = {"/employees/{accountId}/{individualId}", "/employeeById"} )
    public Employee getEmployeeById(@PathVariable("accountId") Optional<String> accId,
                                    @PathVariable("individualId") Optional<String> indId,
                                    @RequestParam("accId") Optional<String> accountId,
                                    @RequestParam("indId") Optional<String> individualId) {
        String accIdVal = null;
        String indIdVal = null;
        if(accountId.isPresent() && individualId.isPresent()) {
            accIdVal = accountId.get();
            indIdVal = individualId.get();
        } else if(accId.isPresent() && indId.isPresent()) {
            accIdVal = accId.get();
            indIdVal = indId.get();
        }
        if(StringUtils.isEmpty(accIdVal.strip()) || StringUtils.isEmpty(indIdVal.strip())) {
            throw new IllegalArgumentException("AccountId or InvidualId is not specified");
        }
        logger.info("accountId=" + accIdVal + "\tindividualId=" + indIdVal);
        return this.employeeService.findById(accIdVal,indIdVal);
    }

    @GetMapping(path = "/employeesByAccountId/{accountId}")
    public List<Employee> getEmployeesByAccountId(@PathVariable("accountId") @Validated String accountId) {
        if(StringUtils.isEmpty(accountId)) {
            throw new IllegalArgumentException("AccountId is not specified");
        }
        return this.employeeService.findByAccountId(accountId);
    }

    @PostMapping(path = "/employees",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> createEmployee(@RequestBody @NotNull Employee employee) {
        var emp = this.employeeService.add(employee);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{accountId}/{individualId}")
                .buildAndExpand(emp.getId().getAccountId(),emp.getId().getIndividualId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(path = "/employees",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateEmployee(@RequestBody @NotNull Employee employee) {
        this.employeeService.update(employee);
        //throw new EmployeeNotFoundException("Employee cannot be updated. Employee with given account id = " + employee.getId().getAccountId() + "and individual id = " + employee.getId().getIndividualId() + " doesn't exist");
        return ResponseEntity.noContent().build();
    }

    /*
    @DeleteMapping(path = "/employees/{accountId}/{individualId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("accountId") @Validated String accountId,
                                               @PathVariable("individualId") @Validated String individualId) {
        var employeeList = this.employeeService.findById(accountId,individualId);
        employeeList.ifPresentOrElse(e -> this.employeeService.delete(accountId, individualId),
                () -> { throw new IllegalArgumentException("Employee cannot be deleted. Employee with given id doesn't exist"); });
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/greet-non-blocking")
    public List<Flux<String>> greetNonBlocking() {
        logger.info("Start NON BLOCKING greetNonBlocking!");
        LocalDateTime start = LocalDateTime.now();
        List<Flux<String>> greetingFluxList = employeeService.greetEmployeesNonBlocking();
        LocalDateTime stop = LocalDateTime.now();
        logger.info("Finished NON BLOCKING greetNonBlocking. Execution time = " + Duration.between(start, stop).toMillis() + " milli seconds");
        return greetingFluxList;
    }

    @GetMapping("/greet-blocking")
    public List<String> greetBlocking() {
        logger.info("Starting BLOCKING greetBlocking");
        LocalDateTime start = LocalDateTime.now();
        List<String> employeeNames = employeeService.findAll().stream().map(Employee::getName).collect(Collectors.toList());
        List<String> greetingMessageList = employeeNames.stream().map(name -> {
            String uri = myCurrencyExchangeServiceUri + "/hello/" + name;
            String greeting = restTemplate.getForObject(uri, String.class);
            logger.info(greeting);
            return greeting;
        }).collect(Collectors.toList());
        LocalDateTime stop = LocalDateTime.now();
        logger.info("Finished BLOCKING greetBlocking. Execution time = " + Duration.between(start, stop).toMillis() + " milli seconds");
        return greetingMessageList;
    }
     */
}