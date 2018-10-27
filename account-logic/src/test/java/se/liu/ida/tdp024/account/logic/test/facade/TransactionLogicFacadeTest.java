package se.liu.ida.tdp024.account.logic.test.facade;

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.logic.impl.facade.TransactionLogicFacadeImpl;
import se.liu.ida.tdp024.account.logic.impl.utils.ApiHelperImpl;

public class TransactionLogicFacadeTest {


    //--- Unit under test ---//
    public TransactionLogicFacade transactionLogicFacade;
    public AccountLogicFacade accountLogicFacade;
    public StorageFacade storageFacade;

    @Before
    public void setup() {
        transactionLogicFacade = new TransactionLogicFacadeImpl(new TransactionEntityFacadeDB());
        accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB(), new ApiHelperImpl());
        storageFacade = new StorageFacadeDB();
    }
    
    @After
    public void clean() {
      if (storageFacade != null)
        storageFacade.emptyStorage();
    }
    
    @Test
    public void testGoodCredit() {
        accountLogicFacade.create("CHECK", "1", "SWEDBANK");
        boolean res = transactionLogicFacade.credit(1, 200);
        Account acc = accountLogicFacade.find("1").get(0);
        Assert.assertTrue(res && acc.getHoldings() == 200);
    }
    
    @Test
    public void testGoodDebit() {
        accountLogicFacade.create("CHECK", "1", "SWEDBANK");
        transactionLogicFacade.credit(1, 200);
        boolean res = transactionLogicFacade.debit(1, 200);
        Account acc = accountLogicFacade.find("1").get(0);
        Assert.assertTrue(res && acc.getHoldings() == 0);
    }
    
    @Test
    public void testBadDebit() {
        accountLogicFacade.create("CHECK", "1", "SWEDBANK");
        boolean res = transactionLogicFacade.debit(1, 200);
        Assert.assertFalse(res);
    }
    
    @Test
    public void testCreditNoExist() {
        boolean res = transactionLogicFacade.credit(9999, 200);
        Assert.assertFalse(res);
    }

    @Test
    public void testDebitNoExist() {
        boolean res = transactionLogicFacade.debit(9999, 200);
        Assert.assertFalse(res);
    }
    
    @Test
    public void testBadAmount() {
        accountLogicFacade.create("CHECK", "1", "SWEDBANK");
        boolean credit = transactionLogicFacade.credit(1, 0);
        Assert.assertFalse(credit);
        boolean debit = transactionLogicFacade.debit(1, 0);
        Assert.assertFalse(debit);
    }
    
    @Test
    public void testFindAllTransactions() {
        accountLogicFacade.create("CHECK", "1", "SWEDBANK");
        transactionLogicFacade.credit(1, 250);
        transactionLogicFacade.debit(1, 220);
        List<Transaction> tas = transactionLogicFacade.transactions(1);
        Transaction credit = tas.get(0);
        Transaction debit = tas.get(1);
        Account acc = accountLogicFacade.find("1").get(0);
        Assert.assertEquals(credit.getAccount().getID(), acc.getID());
        Assert.assertEquals(debit.getAccount().getID(), acc.getID());
        Assert.assertEquals(credit.getTransactionType(), Transaction.Type.CREDIT);
        Assert.assertEquals(debit.getTransactionType(), Transaction.Type.DEBIT);
    }
    
    @Test
    public void testFindNoTransactions() {
        List<Transaction> tas = transactionLogicFacade.transactions(-99);
        Assert.assertTrue(tas.isEmpty());
    }
    
}
