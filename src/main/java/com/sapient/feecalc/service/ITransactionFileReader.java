package com.sapient.feecalc.service;

import com.sapient.feecalc.dto.Transaction;

import java.util.List;

public interface ITransactionFileReader {
    default Transaction createTransaction(String[] elems) {
        return new Transaction(elems);
    }
    List<Transaction> readTranasactions(String filePath);
}
