package org.subhashis.microservices.mycurrencyconversionservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.subhashis.microservices.mycurrencyconversionservice.model.CurrencyBean;
import org.subhashis.microservices.mycurrencyconversionservice.repository.CurrencyRepository;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    public CurrencyBean addCurrency(CurrencyBean currency) {
        return currencyRepository.save(currency);
    }

    public int updateCurrency(CurrencyBean currency) {
        return currencyRepository.setCurrencyBeanBySymbol(currency.getSymbol(),currency.getFullName());
    }

    public void deleteAllInBatch() {
        currencyRepository.deleteAllInBatch();
    }
}