/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.test.facade;

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;

/**
 *
 * @author frazz
 */
public class TransactionEntityFacadeTest {
    //---- Unit under test ----//
    private AccountEntityFacade accountEntityFacade;
    private TransactionEntityFacade transactionEntityFacade;
    private StorageFacade storageFacade;
    private Account account;
    
    @Before
    public void setup() {
        accountEntityFacade = new AccountEntityFacadeDB();
        transactionEntityFacade = new TransactionEntityFacadeDB();
        storageFacade = new StorageFacadeDB();
        account = accountEntityFacade.create(Account.Type.CHECK, "personkey", "bankkey");
    }
    
    @After
    public void clean() {
        storageFacade.emptyStorage();
    }
    
    @Test
    public void testCreditBadAccount() {
        Transaction.Status res = transactionEntityFacade.credit(9000, 200);
        assertEquals(res, Transaction.Status.FAILED);
    }
    
    @Test
    public void testDebitBadAccount() {
        Transaction.Status res = transactionEntityFacade.debit(9000, 200);
        assertEquals(res, Transaction.Status.FAILED);
    }
    
    @Test
    public void testCreditBadAmount() {
        Transaction.Status res = transactionEntityFacade.credit(account.getID(), 0);
        assertEquals(res, Transaction.Status.FAILED);
    }
    
    @Test
    public void testDebitBadAmount() {
        Transaction.Status res = transactionEntityFacade.debit(account.getID(), 0);
        assertEquals(res, Transaction.Status.FAILED);
    }
    
    @Test
    public void testDebitNoHoldings() {
        Transaction.Status res = transactionEntityFacade.debit(account.getID(), 200);
        assertEquals(res, Transaction.Status.FAILED);
    }
    
    @Test
    public void testGoodDebit() {
        transactionEntityFacade.credit(account.getID(), 200);
        Transaction.Status res = transactionEntityFacade.debit(account.getID(), 200);
        assertEquals(Transaction.Status.OK, res);
    }
    
    @Test
    public void testGoodCredit() {
        Transaction.Status res = transactionEntityFacade.credit(account.getID(), 200);
        assertEquals(Transaction.Status.OK, res);
        Account updated = accountEntityFacade.find(account.getPersonKey()).get(0);
        assertEquals(200, updated.getHoldings());
    }
    
    @Test
    public void testFind() {
        transactionEntityFacade.credit(account.getID(), 200);
        List<Transaction> tas = transactionEntityFacade.transactions(account.getID());
        assertEquals(false, tas.isEmpty());
    }
    
    @Test
    public void testFindNoExist() {
        List<Transaction> tas = transactionEntityFacade.transactions(9000);
        assertEquals(true, tas.isEmpty());
    }
}
