/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yawar.bank.utility;

/**
 *
 * @author hp
 */
public class InsufficientFundsException extends Exception {

    private float balance;
    private float amount;

    // Constructor that accepts the current balance and the attempted transfer amount
    public InsufficientFundsException(float balance, float amount) {
        super("Attempted to transfer " + amount + " with only " + balance + " available.");
        this.balance = balance;
        this.amount = amount;
    }

    // Override the toString method to include the exception details
    public String toString() {
        System.out.println("Insufficient Funds");
        return "InsufficientFundsException :  message = " + getMessage();
    }
}


