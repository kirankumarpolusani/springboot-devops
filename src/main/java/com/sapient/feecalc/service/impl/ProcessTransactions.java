package com.sapient.feecalc.service.impl;

import com.sapient.feecalc.constants.TransactionFee;
import com.sapient.feecalc.constants.TransactionType;
import com.sapient.feecalc.dto.Transaction;
import com.sapient.feecalc.service.ITransactionFileReader;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProcessTransactions {

    static String filePath = "/Users/kpolusan/Desktop/data.txt";
    private ITransactionFileReader reader = new TransactionCsvFileReader();

    public static void main(String[] args) throws Exception {
        ProcessTransactions pt = new ProcessTransactions();
        List<Transaction> tx = pt.caliculateProcessingFee(filePath);
        System.out.println(tx.size());
        Comparator<Transaction> comp = Comparator.comparing(Transaction::getClientId)
                .thenComparing(Transaction::getType)
                .thenComparing(Transaction::getTranasactionDate)
                .thenComparing(Transaction::getPriorityFlag);
        Collections.sort(tx, comp);
        System.out.println("Client Id    --  Transaction Type  --   Transaction Date  --  Priority -- Processing Fee");
        for(Transaction t : tx){
            System.out.println(t);
        }
    }

    public List<Transaction>  txList= null;

    public List<Transaction> caliculateProcessingFee(String filePath) {
        txList = reader.readTranasactions(filePath);
        for(int i=0; i<txList.size(); i++){
            Transaction t=txList.get(i);
            if(isIntraDayTransaction(t)){
                t.setTransactionFee(TransactionFee.MIN.getFee());
            } else {
                setNormalTransactionFee(t);
            }
        }
        return txList;
    }

    public void setNormalTransactionFee(Transaction tx) {
        if(tx.getPriorityFlag()){
            tx.setTransactionFee(TransactionFee.HIGH.getFee());
        } else {
            if(TransactionType.SELL.getName().equals(tx.getType()) || TransactionType.WITHDRAW.getName().equals(tx.getType())){
                tx.setTransactionFee(TransactionFee.NORMAL.getFee());
            } else if (TransactionType.BUY.getName().equals(tx.getType()) || TransactionType.DEPOSIT.getName().equals(tx.getType())) {
                tx.setTransactionFee(TransactionFee.LOW.getFee());
            }
        }
    }

    public boolean isIntraDayTransaction(Transaction t) {
        boolean checkFlag = false;
        if(!txList.isEmpty()){
            for(int i=0; i<txList.size(); i++){
                Transaction tx=txList.get(i);
                if(t.equals(tx) && isOppositeTranasactionType(t.getType(), tx.getType())){
                    txList.remove(i);
                    return true;
                }
            }
        }
        return checkFlag;
    }

    public boolean isOppositeTranasactionType(String type1, String type2){
        return (TransactionType.BUY.getName().equals(type1) && TransactionType.SELL.getName().equals(type2))
                || (TransactionType.BUY.getName().equals(type2) && TransactionType.SELL.getName().equals(type1));

    }
}
