/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yawar.bank.bean;

/**
 *
 * @author hp
 */
public class TransferBean {
    private int transactionID;
    private String toAccountNo;
    private String fromAccountNo;
    private String dateOfTransfer;
    private float ammount;

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public String getToAccountNo() {
        return toAccountNo;
    }

    public void setToAccountNo(String toAccountNo) {
        this.toAccountNo = toAccountNo;
    }

    public String getFromAccountNo() {
        return fromAccountNo;
    }

    public void setFromAccountNo(String fromAccountNo) {
        this.fromAccountNo = fromAccountNo;
    }

    public String getDateOfTransfer() {
        return dateOfTransfer;
    }

    public void setDateOfTransfer(String dateOfTransfer) {
        this.dateOfTransfer = dateOfTransfer;
    }

    public float getAmmount() {
        return ammount;
    }

    public void setAmmount(float ammount) {
        this.ammount = ammount;
    }

    public TransferBean(int transactionID, String toAccountNo, String fromAccountNo, String dateOfTransfer, float ammount) {
        this.transactionID = transactionID;
        this.toAccountNo = toAccountNo;
        this.fromAccountNo = fromAccountNo;
        this.dateOfTransfer = dateOfTransfer;
        this.ammount = ammount;
    }

    public TransferBean() {
    }
    
}
