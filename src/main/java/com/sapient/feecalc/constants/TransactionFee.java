package com.sapient.feecalc.constants;

public enum TransactionFee {
    HIGH(500d,"HIGH"),
    NORMAL(100d, "NORMAL"),
    LOW(50d, "LOW"),
    MIN(10d, "MIN");

    private Double fee;
    private String priority;

    TransactionFee(Double fee, String priority){
        this.fee = fee;
        this.priority = priority;
    }

    public Double getFee() {
        return fee;
    }

    public String getPriority() {
        return priority;
    }
}
