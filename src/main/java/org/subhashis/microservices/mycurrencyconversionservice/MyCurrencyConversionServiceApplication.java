package org.subhashis.microservices.mycurrencyconversionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.subhashis.microservices.mycurrencyconversionservice.model.Address;
import org.subhashis.microservices.mycurrencyconversionservice.model.CurrencyBean;
import org.subhashis.microservices.mycurrencyconversionservice.model.Employee;
import org.subhashis.microservices.mycurrencyconversionservice.repository.CurrencyRepository;
import org.subhashis.microservices.mycurrencyconversionservice.service.CurrencyService;
import org.subhashis.microservices.mycurrencyconversionservice.service.EmployeeService;

//@EnableDiscoveryClient
//@EnableFeignClients("org.subhashis.microservices.mycurrencyconversionservice")
@SpringBootApplication
public class MyCurrencyConversionServiceApplication implements CommandLineRunner {

	@Autowired
	private CurrencyService currencyService;

	@Autowired
	private EmployeeService employeeService;

	public static void main(String[] args) {
		SpringApplication.run(MyCurrencyConversionServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Cleanup
        currencyService.deleteAllInBatch();
		employeeService.deleteAllInBatch();

		/*
		// Insert a new Employee in the database
		Employee employee = new Employee(new EmployeeIdentity("E-123", "D-457"),
				"Rajeev Kumar Singh",
				"rajeev@callicoder.com",
				"+91-9999999999");
		 */

		var usdCurrency = new CurrencyBean("USD", "US Dollar");
		currencyService.addCurrency(usdCurrency);
		usdCurrency.setFullName("United States Dollar");
		currencyService.updateCurrency(usdCurrency);
		var address1 = new Address("Marathahalli", "Bangalore");
		var employee1 = new Employee("CG10001", "Subhashis", address1, usdCurrency);
		employeeService.add(employee1);
	}

	@Bean
	public RestTemplate restTemplate() { return new RestTemplate(); }

}
