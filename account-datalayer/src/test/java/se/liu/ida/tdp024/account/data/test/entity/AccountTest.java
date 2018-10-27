/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.test.entity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;

/**
 *
 * @author frazz
 */
public class AccountTest {
    
    @Before
    public void setup() {}
    
    @After
    public void clean() {}
    
    @Test(expected=IllegalArgumentException.class)
    public void testBadID() {
        new AccountDB()
                .setID(-1000);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testBadName() {
        new AccountDB()
                .setPersonKey("");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBadBank() {
        new AccountDB()
                .setBankKey("");
    }
    
    @Test
    public void testGetters() {
        Account account = new AccountDB(0, Account.Type.CHECK, "personKey", "bankKey");
        Assert.assertEquals(account.getID(), 0);
        Assert.assertEquals(account.getAccountType(), Account.Type.CHECK);
        Assert.assertEquals(account.getPersonKey(), "personKey");
        Assert.assertEquals(account.getBankKey(), "bankKey");
        Assert.assertEquals(account.getHoldings(), 0);
    }
    
    @Test
    public void testSetters() {
        Account account = new AccountDB(0, Account.Type.CHECK, "personKey", "bankKey");
        account.setID(5);
        account.setAccountType(Account.Type.SAVINGS);
        account.setPersonKey("1");
        account.setBankKey("SWEDBANK");
        account.setHoldings(100);
        Assert.assertEquals(account.getID(), 5);
        Assert.assertEquals(account.getAccountType(), Account.Type.SAVINGS);
        Assert.assertEquals(account.getPersonKey(), "1");
        Assert.assertEquals(account.getBankKey(), "SWEDBANK");
        Assert.assertEquals(account.getHoldings(), 100);
    }
    
}
