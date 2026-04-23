/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yawar.bank.dao;

import com.yawar.bank.bean.TransferBean;
import com.yawar.bank.utility.ConnectionPool;
import com.yawar.bank.utility.InsufficientFundsException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hp
 */
public class BankDAO {
    static Connection conn;
    public boolean validateAccount(String accountNo){
        conn = ConnectionPool.connectDB();
        boolean r = false;
        String sql ="select * from Account_tbl where Account_no = '"+accountNo+"'";
        Statement stmt;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                r =true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BankDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         
        return r;
    }
    public float findBalance(String accountNo){
        conn = ConnectionPool.connectDB();
        String sql ="select balance from Account_tbl where Account_no = '"+accountNo+"'";
        Statement stmt;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
              if (rs.next()) {
                    return rs.getFloat("balance");
                }
        } catch (SQLException ex) {
            Logger.getLogger(BankDAO.class.getName()).log(Level.SEVERE, null, ex);
        }         
        System.out.println("Account not found.");
        return 0;
    }
      // Start one less than 1000

    public int generateTransactionID() {
        if (lastTransactionID >= 9999) {
            lastTransactionID = 1000; // Reset to 1000 if it reaches 9999
        } else {
            lastTransactionID++;
        }
        return lastTransactionID;
    }
    
    public boolean updateBalance(String accountNo , float newBalance){
        conn =ConnectionPool.connectDB();
        String sql = "update Account_tbl set balance = "+newBalance+" where account_no = '"+accountNo+"'";
        try {
            Statement stmt = conn.createStatement();
            int r = stmt.executeUpdate(sql);
            if(r>0){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BankDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
        
    }
    public boolean transferMoney(TransferBean tb)throws InsufficientFundsException{
        conn = ConnectionPool.connectDB();
        boolean r = false;
        float senderBalance = findBalance(tb.getFromAccountNo());
        float recieverBalance = findBalance(tb.getToAccountNo());
        if (senderBalance < tb.getAmmount()) {
            throw new InsufficientFundsException(senderBalance, tb.getAmmount());
            
        }
        int result=0;
        String sql ="insert into transfer_tbl(TransactionID,account_no,Beneficiary_Account_no,Transaction_Date,Transaction_Amt)values('"+tb.getTransactionID()+"','"+tb.getFromAccountNo()+"','"+tb.getToAccountNo()+"','"+tb.getDateOfTransfer()+"','"+tb.getAmmount()+"')";
            Statement stmt;
        try {
            stmt = conn.createStatement();
            result = stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(BankDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(result>0){
            senderBalance = senderBalance-tb.getAmmount();
        recieverBalance = recieverBalance+tb.getAmmount();
        updateBalance(tb.getFromAccountNo(), senderBalance);
        updateBalance(tb.getToAccountNo(), recieverBalance);
        
            r=true;
        }
        return r;
    }
    public void findTransactionByAccount(String AccountNo){
        conn =ConnectionPool.connectDB();
        String sql = "select Account_tbl.name,transfer_tbl.Account_no,transfer_tbl.transactionID,transfer_tbl.Beneficiary_Account_No,transfer_tbl.Transaction_Amt,transfer_tbl.Transaction_Date from transfer_tbl left join Account_tbl on Account_tbl.Account_no=transfer_tbl.Account_no where transfer_tbl.Account_no = '"+AccountNo+"'";
        try {
           Statement stmt = conn.createStatement();
           ResultSet rs =stmt.executeQuery(sql);
           while(rs.next()){
            System.out.println(""+rs.getString("name")+"\t"+rs.getString("Account_No")+"\t"+rs.getInt("TransactionID")+"\t"+rs.getString("Beneficiary_Account_No")+"\t"+rs.getFloat("Transaction_Amt")+"\t"+rs.getString("Transaction_Date"));
        }
        } catch (SQLException ex) {
            Logger.getLogger(BankDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
     private static int lastTransactionID = 1000;
    public static void main(String[] args) throws InsufficientFundsException {
        BankDAO bd = new BankDAO();
        TransferBean tb =new TransferBean();
        tb.setTransactionID(bd.generateTransactionID());
        tb.setFromAccountNo("512411");
        tb.setToAccountNo("512412");
        tb.setDateOfTransfer(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        tb.setAmmount(999);
//        boolean transferStatus = bd.transferMoney(tb);
//        if(transferStatus==true){
//            System.out.println("Transfer Success");
//            
//        }
//        else{
//            System.out.println("Unsuccess");
//        }
//           bd.findTransactionByAccount("512412");
//        boolean valid = bd.updateBalance(account_no,15000.0f);
//        System.out.println(valid);
//        boolean valid = bd.validateAccount(account_no);
//        System.out.println(valid);
//        float balance = bd.findBalance(account_no);
//        System.out.println(balance);
//        System.out.println(bd.generateTransactionID());
//                System.out.println(bd.generateTransactionID());
//        System.out.println(bd.generateTransactionID());
//        System.out.println(bd.generateTransactionID());
//        System.out.println(bd.generateTransactionID());

    }
}
