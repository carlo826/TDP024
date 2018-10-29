package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;

public interface Account extends Serializable {    
    void setID(long id); long getID();
    void setAccountType(Type accountType); Type getAccountType();
    void setPersonKey(String personKey); String getPersonKey();
    void setBankKey(String bankKey); String getBankKey();
    void setHoldings(int holdings); int getHoldings();
    
    enum Type {
        CHECK,
        SAVINGS
    }
    
}
