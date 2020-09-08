package org.subhashis.microservices.mycurrencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.subhashis.microservices.mycurrencyconversionservice.model.CurrencyConversionBean;

@RestController
public class MyCurrencyConversionController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RestTemplate restTemplate;

	//@Autowired
	//private MyCurrencyExchangeServiceProxy proxy;

	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		String url = "http://localhost:8000/currency-exchange/from/{from}/to/{to}";
		Map<String,String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<CurrencyConversionBean> respEntity = restTemplate.getForEntity(url, CurrencyConversionBean.class, uriVariables);
		CurrencyConversionBean resp = respEntity.getBody();
		logger.info("{}",resp);
		return new CurrencyConversionBean(resp.getId(),resp.getFrom(),resp.getTo(),resp.getConversionMultiple(),quantity,quantity.multiply(resp.getConversionMultiple()),resp.getPort());
	}

	/*
	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		CurrencyConversionBean resp = proxy.retrieveExchangeValue(from, to);
		logger.info("{}",resp);
		return new CurrencyConversionBean(resp.getId(),resp.getFrom(),resp.getTo(),resp.getConversionMultiple(),quantity,quantity.multiply(resp.getConversionMultiple()),resp.getPort());
	}
	 */
}