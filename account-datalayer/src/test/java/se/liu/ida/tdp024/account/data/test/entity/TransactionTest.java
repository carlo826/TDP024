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
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;

/**
 *
 * @author frazz
 */
public class TransactionTest {
    
    @Before
    public void setup() {}
    
    @After
    public void clean() {}
    
    @Test(expected=IllegalArgumentException.class)
    public void testBadID() {
        new TransactionDB()
                .setID(-1000);
    }
    
    @Test
    public void testGetters() {
        Account acc = new AccountDB();
        Transaction tx = new TransactionDB(0, Transaction.Type.CREDIT, 0, Transaction.Status.OK, "created", acc);
        Assert.assertEquals(tx.getID(), 0);
        Assert.assertEquals(tx.getTransactionType(), Transaction.Type.CREDIT);
        Assert.assertEquals(tx.getAmount(), 0);
        Assert.assertEquals(tx.getStatus(),  Transaction.Status.OK);
        Assert.assertEquals(tx.getCreated(), "created");
        Assert.assertEquals(tx.getAccount(), acc);
    }
    
    @Test
    public void testSetters() {
        Account account = new AccountDB();
        Transaction tx = new TransactionDB();
        tx.setID(5);
        tx.setTransactionType(Transaction.Type.DEBIT);
        tx.setAmount(100);
        tx.setStatus(Transaction.Status.FAILED);
        tx.setCreated("12.00");
        tx.setAccount(account);
        Assert.assertEquals(tx.getID(), 5);
        Assert.assertEquals(tx.getTransactionType(), Transaction.Type.DEBIT);
        Assert.assertEquals(tx.getAmount(), 100);
        Assert.assertEquals(tx.getStatus(),  Transaction.Status.FAILED);
        Assert.assertEquals(tx.getCreated(), "12.00");
        Assert.assertEquals(tx.getAccount(), account);
    }
    
}
