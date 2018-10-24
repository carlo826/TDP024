package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author frazz
 */
public interface Transaction extends Serializable {    
    void setID(long id); long getID();
    void setTransactionType(Type transactionType); Type getTransactionType();
    void setAmount(int amount); int getAmount();
    void setStatus(Status status); Status getStatus();
    void setCreated(String created); String getCreated();
    void setAccount(Account account); Account getAccount();
   
    enum Status {
        OK,
        FAILED
    }
    
    enum Type {
        DEBIT,
        CREDIT
    }
}
