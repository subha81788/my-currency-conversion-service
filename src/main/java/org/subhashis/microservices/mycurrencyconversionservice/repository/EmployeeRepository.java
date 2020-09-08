package org.subhashis.microservices.mycurrencyconversionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.subhashis.microservices.mycurrencyconversionservice.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,String> {
}
