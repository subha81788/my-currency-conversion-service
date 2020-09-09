package org.subhashis.microservices.mycurrencyconversionservice.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EmployeeIdentity implements Serializable {
    @NotNull
    @Column(name = "acc_id")
    private String accountId;

    @NotNull
    @Column(name = "ind_id")
    private String individualId;

    public EmployeeIdentity() {
    }

    public EmployeeIdentity(String accountId, String individualId) {
        this.accountId = accountId;
        this.individualId = individualId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getIndividualId() {
        return individualId;
    }

    public void setIndividualId(String individualId) {
        this.individualId = individualId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeIdentity empId = (EmployeeIdentity) o;
        return accountId.equals(empId.accountId) &&
                individualId.equals(empId.individualId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, individualId);
    }
}