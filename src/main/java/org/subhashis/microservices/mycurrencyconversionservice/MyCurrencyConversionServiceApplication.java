package org.subhashis.microservices.mycurrencyconversionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.subhashis.microservices.mycurrencyconversionservice.model.Address;
import org.subhashis.microservices.mycurrencyconversionservice.model.CurrencyBean;
import org.subhashis.microservices.mycurrencyconversionservice.model.Employee;
import org.subhashis.microservices.mycurrencyconversionservice.model.EmployeeIdentity;
import org.subhashis.microservices.mycurrencyconversionservice.service.CurrencyService;
import org.subhashis.microservices.mycurrencyconversionservice.service.EmployeeService;

import java.util.List;

@EnableDiscoveryClient
@SpringBootApplication
public class MyCurrencyConversionServiceApplication {
	@Autowired
	private CurrencyService currencyService;

	@Autowired
	private EmployeeService employeeService;

	public static void main(String[] args) {
		SpringApplication.run(MyCurrencyConversionServiceApplication.class, args);
	}

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() { return new RestTemplate(); }

}
