package org.subhashis.microservices.mycurrencyconversionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.subhashis.microservices.mycurrencyconversionservice.model.CurrencyBean;

import javax.transaction.Transactional;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyBean,String> {

    @Transactional
    @Modifying
    @Query("update CurrencyBean c set c.fullName = :fullName where c.symbol = :symbol")
    public int updateCurrencyBeanBySymbol(@Param("symbol") String symbol, @Param("fullName") String fullName);
}
