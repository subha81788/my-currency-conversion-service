package org.subhashis.microservices.mycurrencyconversionservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.subhashis.microservices.mycurrencyconversionservice.exception.EmployeeNotFoundException;
import org.subhashis.microservices.mycurrencyconversionservice.model.Employee;
import org.subhashis.microservices.mycurrencyconversionservice.model.EmployeeIdentity;
import reactor.core.publisher.Flux;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Value("${my-currency-exchange-service-uri}")
    private String myCurrencyExchangeServiceUri;

    @Value("${my-employee-repository-service-uri}")
    private String myEmployeeRepositoryServiceUri;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<Employee> findAll() {
        ResponseEntity<List<Employee>> response = restTemplate.exchange(
                myEmployeeRepositoryServiceUri + "/employees",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Employee>>(){});
        return response.getBody();
    }

    public Employee findById(String accountId, String individualId) {
        Map<String,String> uriVariables = new HashMap<>();
        uriVariables.put("accountId",accountId);
        uriVariables.put("individualId",individualId);
        return restTemplate.getForObject(
                myEmployeeRepositoryServiceUri + "/employees/{accountId}/{individualId}",
                Employee.class,
                uriVariables);
    }

    public List<Employee> findByAccountId(String accountId) {
        Map<String,String> uriVariables = new HashMap<>();
        uriVariables.put("accountId",accountId);
        ResponseEntity<List<Employee>> response = restTemplate.exchange(
                myEmployeeRepositoryServiceUri + "/employeesByAccountId/{accountId}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Employee>>(){},
                uriVariables);
        return response.getBody();
    }

    public Employee add(Employee employee) {
        return restTemplate.postForObject(
                myEmployeeRepositoryServiceUri + "/employees",
                employee,
                Employee.class);
    }

    public void update(Employee employee) {
        String url = myEmployeeRepositoryServiceUri + "/employees";
        var emp = findById(employee.getId().getAccountId(), employee.getId().getIndividualId());
        emp.setName(employee.getName());
        emp.setAddress(employee.getAddress());
        emp.setCurrency(employee.getCurrency());
        restTemplate.put(url,emp);
    }

    /*
    public void delete(String accountId, String individualId) {
        employeeRepository.deleteByIdAccountIdAndIdIndividualId(accountId, individualId);
    }

    public void deleteAllInBatch() {
        employeeRepository.deleteAllInBatch();
    }

        /*
    public List<Flux<String>> greetEmployeesNonBlocking() {
        List<Flux<String>> greetingMessageList = new ArrayList<>();
        List<String> employeeFirstNames = findAll().stream().map(e -> e.getName().split("\\s+")[0]).collect(Collectors.toList());
        return employeeFirstNames.stream().map(name -> {
            Flux<String> greetingFlux = WebClient.create()
                    .get()
                    .uri(myCurrencyExchangeServiceUri + "/hello/" + name)
                    .retrieve()
                    .bodyToFlux(String.class)
                    .log(logger.getName())
                    .subscribe(e -> greetingMessageList.add(e));
            greetingFlux.subscribe(greeting -> logger.info(greeting.toString()));
            return greetingFlux;
        }).collect(Collectors.toList());

        for(String name : employeeNames) {
            Flux<String> greetingsFlux = WebClient.create()
                    .get()
                    .uri(myCurrencyExchangeServiceUri + "/hello/" + name)
                    .retrieve()
                    .bodyToFlux(String.class);
            greetingsFlux.subscribe(greeting -> logger.info(greeting.toString()));
            greetingMessageList.add(greetingsFlux);
        }
        return greetingMessageList;
         */
    //}

}
