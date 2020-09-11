package org.subhashis.microservices.mycurrencyconversionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.subhashis.microservices.mycurrencyconversionservice.model.Employee;
import org.subhashis.microservices.mycurrencyconversionservice.model.EmployeeIdentity;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,String> {

    @Query("from Employee e where e.id.accountId = :accountId and e.id.individualId = :individualId")
    public Optional<Employee> findByIdAccountIdAndIdIndividualId(@Param("accountId") String accountId, @Param("individualId") String individualId);

    @Query("from Employee e where e.id.accountId = :accountId")
    public Optional<List<Employee>> findByIdAccountId(@Param("accountId") String accountId);

    @Query("select c.fullName From Employee e join e.currency c where e.id.accountId = :accountId and e.id.individualId = :individualId")
    public List<String> getCurrencies(@Param("accountId") String accountId, @Param("individualId") String individualId);

    /*
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Employee e set e.name = :name, e.address.street = :street, e.address.city = :city, e.currency.symbol = :symbol, e.currency.fullName = :fullName where e.id.accountId = :accountId and e.id.individualId = :individualId")
    public int update(@Param("name") String name,
                      @Param("street") String street,
                      @Param("city") String city,
                      @Param("symbol") String symbol,
                      @Param("fullName") String fullName,
                      @Param("accountId") String accountId,
                      @Param("individualId") String individualId);
     */

    @Modifying
    @Transactional
    @Query("delete from Employee e where e.id.accountId = :accountId and e.id.individualId = :individualId ")
    public int deleteByIdAccountIdAndIdIndividualId(@Param(("accountId")) String accountId,
                                                    @Param("individualId") String individualId);
}
