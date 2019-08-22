package com.sapient.feecalc.service.impl;

import com.sapient.feecalc.dto.Transaction;
import com.sapient.feecalc.service.ITransactionFileReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransactionCsvFileReader implements ITransactionFileReader{

    @Override
    public List<Transaction> readTranasactions(String filePath) {
        List<Transaction> txList = new ArrayList<>();
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] tx = line.split(cvsSplitBy);
                txList.add(createTransaction(tx));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return txList;
    }

}
