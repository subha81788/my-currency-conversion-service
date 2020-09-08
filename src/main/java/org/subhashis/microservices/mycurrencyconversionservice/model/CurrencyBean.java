package org.subhashis.microservices.mycurrencyconversionservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "currency")
public class CurrencyBean {
    @Column(name = "symbol")
    @NotNull
    @Id
    private String symbol;

    @Column(name = "long_name")
    private String fullName;

    public CurrencyBean() {
    }

    public CurrencyBean(String symbol, String fullName) {
        this.symbol = symbol;
        this.fullName = fullName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CurrencyBean{");
        sb.append("symbol='").append(symbol).append('\'');
        sb.append(", fullName='").append(fullName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}