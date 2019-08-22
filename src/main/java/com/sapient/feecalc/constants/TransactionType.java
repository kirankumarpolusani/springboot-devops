package com.sapient.feecalc.constants;

public enum TransactionType {
    BUY("BUY"),
    SELL("SELL"),
    DEPOSIT("DEPOSIT"),
    WITHDRAW("WITHDRAW");

    TransactionType(String name){
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
