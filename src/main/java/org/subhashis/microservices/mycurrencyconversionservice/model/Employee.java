package org.subhashis.microservices.mycurrencyconversionservice.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Employee {
    @NotNull
    @EmbeddedId
    private EmployeeIdentity id;

    private String name;

    @Embedded
    private Address address;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "currency_symbol")
    private CurrencyBean currency;

    public Employee() {
    }

    public Employee(EmployeeIdentity id, String name, Address address, CurrencyBean currency) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.currency = currency;
    }

    public EmployeeIdentity getId() {
        return id;
    }

    public void setId(EmployeeIdentity id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public CurrencyBean getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyBean currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Employee{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", address=").append(address);
        sb.append(", currency=").append(currency);
        sb.append('}');
        return sb.toString();
    }
}
