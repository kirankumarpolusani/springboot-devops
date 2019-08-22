package com.sapient.feecalc.dto;

import com.sapient.feecalc.constants.TransactionType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Transaction {

    private String extenalTranasactionId;
    private String clientId;
    private String securityId;
    private String type;
    private LocalDate tranasactionDate;
    private Double marketValue;
    private Boolean priorityFlag;
    private Double transactionFee;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public Transaction() {

    }

    public Transaction(String[] elems) {
        //Need to perform checks
        if(elems != null && elems.length == 7){
            this.extenalTranasactionId = elems[0];
            this.clientId = elems[1];
            this.securityId = elems[2];
            this.type = elems[3];
            this.tranasactionDate = LocalDate.parse(elems[4], formatter);
            this.marketValue = Double.valueOf(elems[5]);
            this.priorityFlag = "Y".equals(elems[6].trim())?true:false;
        }

    }

    public String getExtenalTranasactionId() {
        return extenalTranasactionId;
    }

    public void setExtenalTranasactionId(String extenalTranasactionId) {
        this.extenalTranasactionId = extenalTranasactionId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecurityId() {
        return securityId;
    }

    public void setSecurityId(String securityId) {
        this.securityId = securityId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getTranasactionDate() {
        return tranasactionDate;
    }

    public void setTranasactionDate(LocalDate tranasactionDate) {
        this.tranasactionDate = tranasactionDate;
    }

    public Double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(Double marketValue) {
        this.marketValue = marketValue;
    }

    public Boolean getPriorityFlag() {
        return priorityFlag;
    }

    public void setPriorityFlag(Boolean priorityFlag) {
        this.priorityFlag = priorityFlag;
    }

    public Double getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(Double transactionFee) {
        this.transactionFee = transactionFee;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (clientId != null ? !clientId.equals(that.clientId) : that.clientId != null) return false;
        if (securityId != null ? !securityId.equals(that.securityId) : that.securityId != null) return false;
        return tranasactionDate != null ? tranasactionDate.equals(that.tranasactionDate) : that.tranasactionDate == null;
    }

    @Override
    public int hashCode() {
        int result = clientId != null ? clientId.hashCode() : 0;
        result = 31 * result + (securityId != null ? securityId.hashCode() : 0);
        result = 31 * result + (tranasactionDate != null ? tranasactionDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return  clientId +
                "           || " + type +
                "           || " + tranasactionDate +
                "           || " + priorityFlag +
                "           || $" + transactionFee;
    }
}
