package org.subhashis.microservices.mycurrencyconversionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.subhashis.microservices.mycurrencyconversionservice.model.Address;
import org.subhashis.microservices.mycurrencyconversionservice.model.CurrencyBean;
import org.subhashis.microservices.mycurrencyconversionservice.model.Employee;
import org.subhashis.microservices.mycurrencyconversionservice.model.EmployeeIdentity;
import org.subhashis.microservices.mycurrencyconversionservice.service.CurrencyService;
import org.subhashis.microservices.mycurrencyconversionservice.service.EmployeeService;

import java.util.List;

//@EnableDiscoveryClient
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

		var usdCurrency = new CurrencyBean("USD", "US Dollar");
		var gbpCurrency = new CurrencyBean("GBP", "Great Britain Pound");
		currencyService.addCurrency(usdCurrency);
        currencyService.addCurrency(gbpCurrency);
		usdCurrency.setFullName("United States Dollar");
		currencyService.updateCurrency(usdCurrency);

		var address1 = new Address("Marathahalli", "Bangalore");
		var employee1 = new Employee(new EmployeeIdentity("CG-DELL6", "10001"),
				"Subhashis Nath", address1, usdCurrency);

		var address2 = new Address("Bellandur", "Bangalore");
		var employee2 = new Employee(new EmployeeIdentity("CG-DELL6", "10002"),
				"Sandeep Barla", address2, usdCurrency);

		var address3 = new Address("White Field", "Bangalore");
		var employee3 = new Employee(new EmployeeIdentity("CG-DELL6", "10003"),
				"Divyashree TA", address3, gbpCurrency);

		var address4 = new Address("Behala", "Kolkata");
		var employee4 = new Employee(new EmployeeIdentity("TCS-JJ", "50001"),
				"Saikat Mitra", address4, gbpCurrency);

		employeeService.addAll(List.of(employee1, employee2, employee3, employee4));
	}

	@Bean
	public RestTemplate restTemplate() { return new RestTemplate(); }

}
