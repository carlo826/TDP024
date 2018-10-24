/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.impl.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

/**
 *
 * @author frazz
 */
@Entity
public class TransactionDB implements Transaction { 

    public TransactionDB(long id, Type type, int amount, Status status, String created, Account account) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.status = status;
        this.created = created;
        this.account = account;
    }

    public TransactionDB() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Transaction.Type type;
    private int amount;
    private Transaction.Status status;
    private String created;
    
    @ManyToOne(targetEntity = AccountDB.class)
    private Account account;
    
    @Override
    public void setID(long id) {
        if (id < 0) 
            throw new IllegalArgumentException("ID not valid");
        this.id = id;
    }

    @Override
    public long getID() {
        return id;
    }

    @Override
    public void setTransactionType(Type transactionType) {
        this.type = transactionType;
    }

    @Override
    public Type getTransactionType() {
        return type;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public String getCreated() {
        return created;
    }

    @Override
    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public Account getAccount() {
        return account;
    }
    
}

