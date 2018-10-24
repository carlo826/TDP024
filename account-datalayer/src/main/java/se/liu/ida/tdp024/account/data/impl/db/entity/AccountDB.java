package se.liu.ida.tdp024.account.data.impl.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import se.liu.ida.tdp024.account.data.api.entity.Account;

@Entity
public class AccountDB implements Account {

    public AccountDB(long id, Type accountType, String personKey, String bankKey) {
        this.id = id;
        this.accountType = accountType;
        this.personKey = personKey;
        this.bankKey = bankKey;
    }

    public AccountDB() {
    }

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Account.Type accountType;
    private String personKey;
    private String bankKey;
    private int holdings = 0;
    
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
    public void setPersonKey(String personKey) {
        if (personKey == null || "".equals(personKey))
            throw new IllegalArgumentException("PersonKey is not valid");
        this.personKey = personKey;
    }

    @Override
    public String getPersonKey() {
        return personKey;
    }

    @Override
    public void setAccountType(Type accountType) {
        this.accountType = accountType;
    }

    @Override
    public Type getAccountType() {
        return accountType;
    }

    @Override
    public void setBankKey(String bankKey) {
        if (bankKey == null || "".equals(bankKey))
            throw new IllegalArgumentException("BankKey is not valid");
        this.bankKey = bankKey;
    }

    @Override
    public String getBankKey() {
        return bankKey;
    }

    @Override
    public void setHoldings(int holdings) {
        /* We assume holdings can be below zero */
        this.holdings = holdings;
    }

    @Override
    public int getHoldings() {
        return holdings;
    }
    
}
